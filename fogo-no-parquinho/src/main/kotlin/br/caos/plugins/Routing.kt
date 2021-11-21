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
        loginRoute(userControl)
        createAccountRoute(userControl)

        getAllUsersRoute(userControl)
        getUserRolesRoute(userControl)
        getUserInfoRoute(userControl)
        getUserReviewsRoute(userControl, reviewControl)

        getAllSubjectRoute(subjectControl)
        getSubjectInfoRoute(subjectControl)
        getSubjectReviewsRoute(subjectControl, reviewControl)

        authenticate ("auth-jwt") {
            teachOrFavSubjectRoute(userControl)
            reviewUserRoute(userControl,reviewControl)

            createSubjectRoute(subjectControl)
            reviewSubjectRoute(subjectControl,userControl,reviewControl)
        }
    }

}
