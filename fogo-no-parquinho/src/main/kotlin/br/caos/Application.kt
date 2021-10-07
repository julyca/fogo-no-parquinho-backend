package br.caos

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import br.caos.plugins.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun main() {
    embeddedServer(Netty, port = 4040, host = "0.0.0.0") {
        routing {
            get("/"){
                call.respondText("Hello World!")
            }
        }
        //configureRouting()
    }.start(wait = true)
}
