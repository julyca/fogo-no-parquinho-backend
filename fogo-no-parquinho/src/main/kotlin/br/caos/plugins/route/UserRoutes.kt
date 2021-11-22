package br.caos.plugins.route

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

/*** Método que lista todos os usuários cadastrados (SEM PAGINAÇÃO).
 *  @param userControl referência ao controller de usuário para efetuar as operações
 *  @return 200: listagem de usuários como objeto JSON;
 *  500: erro ocorrido ao tentar realizar listagem.
 */
fun Route.getAllUsersRoute(userControl: UserController){
    get("/users") {
        println("[${LocalDateTime.now()}] GET users") // Exibindo quando o método foi chamado para fins de Log
        val userList = Json.encodeToString(userControl.listAllUsers())
        call.respond(userList)
    }
}

/*** Método que lista todos os tipos de perfil de usuário.
 *  @param userControl referência ao controller de usuários para efetuar as operações
 *  @return 200: listagem com os perfis de usuários existentes;
 *  500: erro ocorrido ao tentar realizar listagem.
 */
fun Route.getUserRolesRoute(userControl: UserController){
    get("/users/roles") {
        println("[${LocalDateTime.now()}] GET users/role") // Exibindo quando o método foi chamado para fins de Log
        val listRoles = Json.encodeToString(userControl.listAllRoles())
        call.respond(listRoles)
    }
}

/*** Método que consulta as informações de um usuário.
 *  @param userControl referência ao controller de usuários para efetuar as operações
 *  @return 200: JSON object com os dados do usuário;
 *  404: texto informando que não existe usuário com o código informado;
 *  500: erro ocorrido ao tentar realizar listagem.
 */
fun Route.getUserInfoRoute(userControl: UserController){
    get("/users/{userCode}") {
        println("[${LocalDateTime.now()}] GET users/${call.parameters["userCode"].toString()}/review") // Exibindo quando o método foi chamado para fins de Log
        val userInfo = userControl.getUserInfo(call.parameters["userCode"].toString())
        if (userInfo == null) {
            call.respond(HttpStatusCode.NotFound,"Não existe um usuário com o código informado")
        }
        call.respond(Json.encodeToString(userInfo))
    }
}

/*** Método que lista todas as avaliações feitas sobre UM usuário (SEM PAGINAÇÃO).
 *  @param userControl referência ao controller de usuários para efetuar as operações
 *  @param reviewControl referência ao controller de avaliações para efetuar as operações
 *  @return 200: listagem de avaliações como objeto JSON;
 *  404: texto informando que não existe usuário com o código informado;
 *  500: erro ocorrido ao tentar realizar listagem.
 */
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

/*** Método que permite a criação de um relacionamento entre Disciplina e Usuário (EXIGE AUTENTICAÇÃO).
 * Recebe JSON Body com o id da disciplina a ser vinculada (subjectID)
 * Se o usuário for de perfil professor -> Usuário leciona Disciplina
 * Se o usuário for de perfil aluno -> Aluno cursa/cursou Disciplina
 *  @param userControl referência ao controller de usuário para efetuar as operações
 *  @return 200: texto informando registro efetuado com sucesso;
 *  500: texto informando erro ao realizar o registro;
 */
fun Route.teachOrFavSubjectRoute(userControl: UserController){
    post("/users/{userCode}/subject") {
        println("[${LocalDateTime.now()}] POST users/${call.parameters["subjectCode"].toString()}/subject") // Exibindo quando o método foi chamado para fins de Log
        var relateDto = Json.decodeFromString<RelateSubjectDto>(call.receiveText()) // Adquirindo dados da requisição
        // recuperando dados do usuário da sessão
        val principal = call.principal<JWTPrincipal>()
        val currentUser = userControl.getUserByName(principal!!.payload.getClaim("username").asString())

        val wasCreated = userControl.relateSubject(relateDto.subjectId, currentUser!!.id)
        if (wasCreated) // Se o relacionamento foi registrado com sucesso
            call.respondText("Vinculo criado com sucesso!")
        else
            call.respond(HttpStatusCode.InternalServerError, "Ocorreu um erro ao tentar vincular o usuário à disciplina")

    }
}

/*** Método que permite o registro de uma nova REVIEW feita sobre UM usuário ESPECÍFICO (EXIGE AUTENTICAÇÃO).
 *  Recebe JSON Body com o texto da avaliação (feedback) e a nota (score)
 *  @param userControl referência ao controller de usuário para efetuar as operações
 *  @param reviewControl referência ao controller de avaliações para efetuar as operações
 *  @return 200: texto informando registro efetuado com sucesso;
 *  500: texto informando erro ao realizar o registro;
 *  404: texto informando que não existe usuário com o código informado;
 *  400: texto informando que o JSON enviado está no formato incorreto.
 */
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
