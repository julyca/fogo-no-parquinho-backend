package br.caos.plugins

import br.caos.controller.UserController
import br.caos.plugins.route.createAccountRoute
import br.caos.plugins.route.loginRoute
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting(userControl: UserController) {
    // Starting point for a Ktor app:
    routing {
        loginRoute(userControl)
        createAccountRoute(userControl)
    }

}
