package br.caos.plugins.route.users

import br.caos.controller.ReviewController
import br.caos.controller.UserController
import br.caos.view.RelateSubjectDto
import br.caos.view.ReviewDto
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
import java.time.LocalDateTime

fun Route.getAllUsersRoute(userControl: UserController){
    get("/users") {
        println("[${LocalDateTime.now()}] GET users") // Exibindo quando o método foi chamado para fins de Log
        val userList = Json.encodeToString(userControl.listAllUsers())
        call.respond(userList)
    }
}

fun Route.getUserRolesRoute(userControl: UserController){
    get("/users/roles") {
        println("[${LocalDateTime.now()}] GET users/role") // Exibindo quando o método foi chamado para fins de Log
        val listRoles = Json.encodeToString(userControl.listAllRoles())
        call.respond(listRoles)
    }
}

fun Route.getUserInfoRoute(userControl: UserController){
    get("/users/{userCode}") {
        println("[${LocalDateTime.now()}] GET users/${call.parameters["userCode"].toString()}/review") // Exibindo quando o método foi chamado para fins de Log
        val userInfo = Json.encodeToString(userControl.getUserInfo(call.parameters["userCode"].toString()))
        call.respond(userInfo)
    }
}

fun Route.getUserReviewsRoute(userControl: UserController, reviewControl: ReviewController){
    get("/users/{userCode}/review") {
        println("[${LocalDateTime.now()}] GET users/${call.parameters["userCode"].toString()}/review")
        val userInfo = userControl.getUserInfo(call.parameters["userCode"].toString())
        if (userInfo == null) {
            call.respond(HttpStatusCode.NotFound,"Não existe um usuário com o código informado")
        }
        var listReviews = Json.encodeToString(reviewControl.listUserReviews(userInfo!!.id)) // Adquirindo dados da requisição
        call.respond(listReviews)
    }
}

fun Route.teachOrFavSubjectRoute(userControl: UserController){
    post("/users/{userCode}/subject") {
        println("[${LocalDateTime.now()}] POST users/${call.parameters["subjectCode"].toString()}/subject") // Exibindo quando o método foi chamado para fins de Log
        var relateDto = Json.decodeFromString<RelateSubjectDto>(call.receiveText()) // Adquirindo dados da requisição
        // recuperando dados do usuário da sessão
        val principal = call.principal<JWTPrincipal>()
        val currentUser = userControl.getUserByName(principal!!.payload.getClaim("username").asString())

        userControl.relateSubject(relateDto.subjectId, currentUser!!.id)
    }
}

fun Route.reviewUserRoute(userControl: UserController, reviewControl: ReviewController){
    post("/users/{userCode}/review") {
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
