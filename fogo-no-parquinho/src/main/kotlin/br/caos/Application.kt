package br.caos

import br.caos.controller.UserController
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import br.caos.shared.SharedPaths
import br.caos.view.LoginDto
import br.caos.view.UserDto
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Instant
import java.util.*

fun main() {
    val userControl : UserController = UserController()
    embeddedServer(Netty, port = 4040, host = "0.0.0.0") {
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
                    val loginInfo = Json.decodeFromString<LoginDto>(call.receiveText()) // Adquirindo dados da requisição
                    val wasLoginSuccessful = userControl.login(loginInfo) // Validando os dados recebidos
                    if (wasLoginSuccessful){ // Se os dados estão corretos (login valido), gerar token de autenticação
                        val token = JWT.create()
                            .withAudience(SharedPaths.JWT_AUD)
                            .withIssuer(SharedPaths.JWT_ISS)
                            .withClaim("username", loginInfo.username)
                            .withExpiresAt(Date(System.currentTimeMillis() + 900000))
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

            authenticate ("auth-jwt") {
                route("/users") {
                    get {
                        val userList = Json.encodeToString(userControl.listAllUsers())
                        call.respond(userList)
                    }
                    get("/roles") {
                        val listRoles = Json.encodeToString(userControl.listAllRoles())
                        call.respond(listRoles)
                    }
                    route("/{userCode}") {
                        get {
                            val userInfo = Json.encodeToString(userControl.getUserInfo(call.parameters["userCode"].toString()))
                            call.respond(userInfo)
                        }
                        post("/subject") {

                        }
                        post("/review") {

                        }
                    }
                }

                route("/subjects") {
                    get {

                    }
                    post {

                    }
                    route("/{subjectCode}") {
                        get {

                        }
                        post("/review") {

                        }
                    }
                }
            }
        }
        //configureRouting()
    }.start(wait = true)
}
