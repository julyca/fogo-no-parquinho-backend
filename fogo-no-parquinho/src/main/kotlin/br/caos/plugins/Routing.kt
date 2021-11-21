package br.caos.plugins

import br.caos.controller.ReviewController
import br.caos.controller.UserController
import br.caos.plugins.route.createAccountRoute
import br.caos.plugins.route.loginRoute
import br.caos.plugins.route.users.*
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*

fun Application.configureRouting(userControl: UserController, reviewControl: ReviewController) {
    // Starting point for a Ktor app:
    routing {
        loginRoute(userControl)
        createAccountRoute(userControl)

        getAllUsersRoute(userControl)
        getUserRolesRoute(userControl)
        getUserInfoRoute(userControl)
        getUserReviewsRoute(userControl, reviewControl)

        authenticate ("auth-jwt") {
            teachOrFavSubjectRoute(userControl)
            reviewUserRoute(userControl,reviewControl)
        }
    }

}
