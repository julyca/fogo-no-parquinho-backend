package br.caos

import br.caos.controller.ReviewController
import br.caos.controller.SubjectController
import br.caos.controller.UserController
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import br.caos.shared.SharedPaths
import br.caos.view.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

fun main() {
    val userControl = UserController()
    val reviewControl = ReviewController()
    val subjectControl = SubjectController()

    embeddedServer(Netty, port = 4040, host = "0.0.0.0") {
        install(CORS){
            anyHost()
            method(HttpMethod.Options)
            header(HttpHeaders.Authorization)
        }
        install(Authentication){
            jwt("auth-jwt"){
               verifier(JWT.require(Algorithm.HMAC256(SharedPaths.JWT_SECRET))
                       .withAudience(SharedPaths.JWT_AUD)
                       .withIssuer(SharedPaths.JWT_ISS)
                       .build())
               // validando credenciais: username não pode ser nulo; username deve existir na base; o token não pode ter expirado
               validate { jwtCredential ->
                   if(jwtCredential.payload.getClaim("username").asString() != "" && jwtCredential.payload.expiresAt > Date.from(Instant.now()))
                       if(userControl.userExists(jwtCredential.payload.getClaim("username").asString())) // verificando se usuário do token existe
                           JWTPrincipal(jwtCredential.payload)
                       else null
                   else null
               }
            }
        }
        routing {
            get("/"){
                call.respondText("Hello World!")
            }

            post("/login") {
                try {
                    println("[${LocalDateTime.now()}] POST login") // Exibindo quando o método foi chamado para fins de Log
                    val loginInfo = Json.decodeFromString<LoginDto>(call.receiveText()) // Adquirindo dados da requisição
                    val wasLoginSuccessful = userControl.login(loginInfo) // Validando os dados recebidos
                    if (wasLoginSuccessful){ // Se os dados estão corretos (login valido), gerar token de autenticação
                        val token = JWT.create()
                            .withAudience(SharedPaths.JWT_AUD)
                            .withIssuer(SharedPaths.JWT_ISS)
                            .withClaim("username", loginInfo.username)
                            .withExpiresAt(Date(System.currentTimeMillis() + 90000000)) // OBS: Por questões de segurança, o tempo de expiração é de 15 min, MAS por questão de praticidade no estudo vai ser de 25 horas ( ❛ ᴗ ❛.)
                            .sign(Algorithm.HMAC256(SharedPaths.JWT_SECRET))
                        call.respond(HttpStatusCode.OK,"{ \"token\" : \"$token\"}") // devolvendo token para o front-end
                    } else {
                        call.respond(HttpStatusCode.Unauthorized,"Nome de usuário ou senha inválidos.")
                    }

                } catch (ex: Exception){
                    ex.printStackTrace()
                    call.respond(HttpStatusCode.BadRequest,"O formato dos dados enviados está incorreto.")
                }

            }

            post("/register") {
                try {
                    println("[${LocalDateTime.now()}] POST register") // Exibindo quando o método foi chamado para fins de Log
                    val userDto = Json.decodeFromString<UserDto>(call.receiveText()) // Adquirindo dados da requisição
                    val wasCreated = userControl.registerUser(userDto)
                    if (wasCreated) // Se usuário foi cadastrado com sucesso
                        call.respondText("Usuário criado com sucesso!")
                    else
                        call.respond(HttpStatusCode.InternalServerError, "Ocorreu um erro ao tentar cadastrar o usuário")
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    call.respond(HttpStatusCode.BadRequest,"O formato dos dados enviados está incorreto.")
                }

            }

            route("/users") {
                get {
                    println("[${LocalDateTime.now()}] GET users") // Exibindo quando o método foi chamado para fins de Log
                    val userList = Json.encodeToString(userControl.listAllUsers())
                    call.respond(userList)
                }
                get("/roles") {
                    println("[${LocalDateTime.now()}] GET users/role") // Exibindo quando o método foi chamado para fins de Log
                    val listRoles = Json.encodeToString(userControl.listAllRoles())
                    call.respond(listRoles)
                }
                route("/{userCode}") {
                    get {
                        println("[${LocalDateTime.now()}] GET users/${call.parameters["userCode"].toString()}/review") // Exibindo quando o método foi chamado para fins de Log
                        val userInfo = Json.encodeToString(userControl.getUserInfo(call.parameters["userCode"].toString()))
                        call.respond(userInfo)
                    }
                    get("/review") {
                        println("[${LocalDateTime.now()}] GET users/${call.parameters["userCode"].toString()}/review")
                        val userInfo = userControl.getUserInfo(call.parameters["userCode"].toString())
                        if (userInfo == null) {
                            call.respond(HttpStatusCode.NotFound,"Não existe um usuário com o código informado")
                        }
                        var listReviews = Json.encodeToString(reviewControl.listUserReviews(userInfo!!.id)) // Adquirindo dados da requisição
                        call.respond(listReviews)
                    }
                }
            }

            route("/subjects") {
                get {
                    val subList = Json.encodeToString(subjectControl.listAllSubjects())
                    call.respond(subList)
                }
                route("/{subjectCode}") {
                    get {
                        println("[${LocalDateTime.now()}] GET subjects/${call.parameters["subjectCode"].toString()}") // Exibindo quando o método foi chamado para fins de Log
                        val subInfo = Json.encodeToString(subjectControl.getSubjectInfo(call.parameters["subjectCode"].toString()))
                        call.respond(subInfo)
                    }
                    get("/review") {
                        println("[${LocalDateTime.now()}] GET subjects/${call.parameters["subjectCode"].toString()}/review")
                        val subInfo = subjectControl.getSubjectInfo(call.parameters["subjectCode"].toString())
                        if (subInfo == null) {
                            call.respond(HttpStatusCode.NotFound,"Não existe uma disciplina com o código informado")
                        }
                        var listReviews = Json.encodeToString(reviewControl.listSubjectReviews(subInfo!!.id)) // Adquirindo dados da requisição
                        call.respond(listReviews)
                    }
                }
            }

            authenticate ("auth-jwt") {
                route("/users") {
                    route("/{userCode}") {
                        post("/subject") {
                            println("[${LocalDateTime.now()}] POST users/${call.parameters["subjectCode"].toString()}/subject") // Exibindo quando o método foi chamado para fins de Log
                            var relateDto = Json.decodeFromString<RelateSubjectDto>(call.receiveText()) // Adquirindo dados da requisição
                            // recuperando dados do usuário da sessão
                            val principal = call.principal<JWTPrincipal>()
                            val currentUser = userControl.getUserByName(principal!!.payload.getClaim("username").asString())

                            userControl.relateSubject(relateDto.subjectId, currentUser!!.id)
                        }
                        post("/review") {
                            println("[${LocalDateTime.now()}] POST users/${call.parameters["userCode"].toString()}/review") // Exibindo quando o método foi chamado para fins de Log

                            val userInfo = userControl.getUserInfo(call.parameters["userCode"].toString())
                            if (userInfo == null)
                                call.respond(HttpStatusCode.NotFound,"Não existe uma disciplina com o código informado")

                            try {
                                var reviewDto = Json.decodeFromString<ReviewDto>(call.receiveText()) // Adquirindo dados da requisição
                                // recuperando dados do usuário da sessão
                                val principal = call.principal<JWTPrincipal>()
                                val currentUser = userControl.getUserByName(principal!!.payload.getClaim("username").asString())

                                if(currentUser == null)
                                    call.respond(HttpStatusCode.Unauthorized,"Ocorreu um erro ao validar os dados da sessão")
                                if(currentUser!!.id == userInfo!!.id)
                                    call.respond(HttpStatusCode.BadRequest,"Você não pode se auto-avaliar ;)")

                                reviewDto.reviewerId = currentUser!!.id

                                val wasCreated = reviewControl.reviewUser(userInfo!!.id, reviewDto)
                                if (wasCreated)
                                    call.respond(HttpStatusCode.OK,"Review feita com sucesso! Agradecemos pela avaliação!")
                                else
                                    call.respond(HttpStatusCode.InternalServerError, "Ocorreu um erro ao tentar registrar a review")
                            } catch (ex: Exception){
                                ex.printStackTrace()
                                call.respond(HttpStatusCode.BadRequest,"O formato dos dados enviados está incorreto.")
                            }
                        }
                    }
                }

                route("/subjects") {
                    post {
                        println("[${LocalDateTime.now()}] POST subjects") // Exibindo quando o método foi chamado para fins de Log
                        try {
                            val subDto = Json.decodeFromString<SubjectDto>(call.receiveText()) // Adquirindo dados da requisição
                            val wasCreated = subjectControl.registerSubject(subDto)
                            if (wasCreated) // Se usuário foi cadastrado com sucesso
                                call.respondText("Disciplina criada com sucesso!")
                            else
                                call.respond(HttpStatusCode.InternalServerError, "Ocorreu um erro ao tentar criar a Disciplina")
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            call.respond(HttpStatusCode.BadRequest,"O formato dos dados enviados está incorreto.")
                        }
                    }
                    route("/{subjectCode}") {
                        post("/review") {
                            println("[${LocalDateTime.now()}] POST subjects/${call.parameters["subjectCode"].toString()}/review") // Exibindo quando o método foi chamado para fins de Log

                            val subjectInfo = subjectControl.getSubjectInfo(call.parameters["subjectCode"].toString())
                            if (subjectInfo == null)
                                call.respond(HttpStatusCode.NotFound,"Não existe uma disciplina com o código informado")

                            try {
                                var reviewDto = Json.decodeFromString<ReviewDto>(call.receiveText()) // Adquirindo dados da requisição
                                // recuperando dados do usuário da sessão
                                val principal = call.principal<JWTPrincipal>()
                                val currentUser = userControl.getUserByName(principal!!.payload.getClaim("username").asString())
                                reviewDto.reviewerId = currentUser!!.id

                                val wasCreated = reviewControl.reviewSubject(subjectInfo!!.id, reviewDto)
                                if (wasCreated)
                                    call.respond(HttpStatusCode.OK,"Review feita com sucesso! Agradecemos pela avaliação!")
                                else
                                    call.respond(HttpStatusCode.InternalServerError, "Ocorreu um erro ao tentar registrar a review")
                            } catch (ex: Exception){
                                ex.printStackTrace()
                                call.respond(HttpStatusCode.BadRequest,"O formato dos dados enviados está incorreto.")
                            }
                        }
                    }
                }
            }
        }
        //configureRouting()
    }.start(wait = true)
}
