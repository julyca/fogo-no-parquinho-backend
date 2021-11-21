package br.caos.plugins.route

import br.caos.controller.ReviewController
import br.caos.controller.SubjectController
import br.caos.controller.UserController
import br.caos.view.ReviewDto
import br.caos.view.SubjectDto
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

/*** Método que lista todas as disciplinas cadastradas (SEM PAGINAÇÃO).
 *  @param subjectControl referência ao controller de disciplinas para efetuar as operações
 *  @return 200: listagem de disciplinas como objeto JSON;
 *  500: erro ocorrido ao tentar realizar listagem.
 */
fun Route.getAllSubjectRoute(subjectControl: SubjectController){
    get("/subjects") {
        val subList = Json.encodeToString(subjectControl.listAllSubjects())
        call.respond(subList)
    }
}

/*** Método que consulta as informações de uma disciplina.
 *  @param subjectControl referência ao controller de disciplinas para efetuar as operações
 *  @return 200: JSON object com os dados da disciplina;
 *  404: texto informando que não existe matéria com o código informado;
 *  400: texto informando que o JSON enviado está no formato incorreto;
 *  500: erro ocorrido ao tentar realizar listagem.
 */
fun Route.getSubjectInfoRoute(subjectControl: SubjectController){
    get("/subjects/{subjectCode}") {
        println("[${LocalDateTime.now()}] GET subjects/${call.parameters["subjectCode"].toString()}") // Exibindo quando o método foi chamado para fins de Log
        val subInfo = subjectControl.getSubjectInfo(call.parameters["subjectCode"].toString())
        if (subInfo == null) {
            call.respond(HttpStatusCode.NotFound,"Não existe uma disciplina com o código informado")
        }
        call.respond(Json.encodeToString(subInfo))
    }
}

/*** Método que lista todas as avaliações feitas sobre UMA disciplina (SEM PAGINAÇÃO).
 *  @param subjectControl referência ao controller de disciplinas para efetuar as operações
 *  @param reviewControl referência ao controller de avaliações para efetuar as operações
 *  @return 200: listagem de avaliações como objeto JSON;
 *  404: texto informando que não existe matéria com o código informado;
 *  500: erro ocorrido ao tentar realizar listagem.
 */
fun Route.getSubjectReviewsRoute(subjectControl: SubjectController, reviewControl: ReviewController){
    get("/subjects/{subjectCode}/review") {
        println("[${LocalDateTime.now()}] GET subjects/${call.parameters["subjectCode"].toString()}/review")
        val subInfo = subjectControl.getSubjectInfo(call.parameters["subjectCode"].toString())
        if (subInfo == null) {
            call.respond(HttpStatusCode.NotFound,"Não existe uma disciplina com o código informado")
        }
        var listReviews = Json.encodeToString(reviewControl.listSubjectReviews(subInfo!!.id)) // Adquirindo dados da requisição
        call.respond(listReviews)
    }
}

/*** Método que permite o registro de uma nova disciplina (EXIGE AUTENTICAÇÃO).
 *  Recebe JSON Body com nome da disciplina (name), código (code) e descrição (description)
 *  @param subjectControl referência ao controller de disciplinas para efetuar as operações
 *  @return 200: texto informando registro efetuado com sucesso;
 *  500: texto informando erro ao realizar o registro;
 *  400: texto informando que o JSON enviado está no formato incorreto.
 */
fun Route.createSubjectRoute(subjectControl: SubjectController){
    post("/subjects") {
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
}

/*** Método que permite o registro de uma nova REVIEW feita sobre UMA disciplina ESPECÍFICA (EXIGE AUTENTICAÇÃO).
 *  Recebe JSON Body com o texto da avaliação (feedback) e a nota (score)
 *  @param subjectControl referência ao controller de disciplinas para efetuar as operações
 *  @param userControl referência ao controller de usuário para efetuar as operações
 *  @param reviewControl referência ao controller de avaliações para efetuar as operações
 *  @return 200: texto informando registro efetuado com sucesso;
 *  500: texto informando erro ao realizar o registro;
 *  404: texto informando que não existe matéria com o código informado;
 *  400: texto informando que o JSON enviado está no formato incorreto.
 */
fun Route.reviewSubjectRoute(subjectControl: SubjectController, userControl: UserController, reviewControl: ReviewController){
    post("/subjects/{subjectCode}/review") {
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