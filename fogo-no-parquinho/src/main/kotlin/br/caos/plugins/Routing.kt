package br.caos.plugins

import br.caos.controller.ReviewController
import br.caos.controller.SubjectController
import br.caos.controller.UserController
import br.caos.plugins.route.*
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*

fun Application.configureRouting(userControl: UserController, subjectControl: SubjectController, reviewControl: ReviewController) {
    routing {
        // Conta do Usuário -> Rotas Públicas
        loginRoute(userControl)
        createAccountRoute(userControl)

        // Usuários -> Rotas Públicas
        getAllUsersRoute(userControl)
        getUserRolesRoute(userControl)
        getUserInfoRoute(userControl)
        getUserReviewsRoute(userControl, reviewControl)

        // Disciplinas -> Rotas Públicas
        getAllSubjectRoute(subjectControl)
        getSubjectInfoRoute(subjectControl)
        getSubjectReviewsRoute(subjectControl, reviewControl)

        authenticate ("auth-jwt") {
            // Usuários -> Rotas Protegidas
            teachOrFavSubjectRoute(userControl)
            reviewUserRoute(userControl,reviewControl)

            // Disciplinas -> Rotas Protegidas
            createSubjectRoute(subjectControl)
            reviewSubjectRoute(subjectControl,userControl,reviewControl)
        }
    }

}
