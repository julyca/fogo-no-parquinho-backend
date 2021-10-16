package br.caos

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import br.caos.plugins.*
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun main() {
    embeddedServer(Netty, port = 4040, host = "0.0.0.0") {
        routing {
            get("/"){
                call.respondText("Hello World!")
            }
            post("/login") {
                val formParameters = call.receiveParameters()
                val username = formParameters["username"].toString()
                call.respondText("$username")
            }
            post("/register"){
                val formParameters = call.receiveParameters()
                val username = formParameters["username"].toString()
                call.respondText("$username")
            }

            route("/users"){
                route("/{userCode}"){
                    get {

                    }
                    post("/subject"){

                    }
                    post("/review"){

                    }
                }
                get("/roles"){

                }
            }

            route("/subjects"){
                get {

                }
                post {

                }
                route("/{subjectCode}"){
                    get{

                    }
                    post("/review"){

                    }
                    post("/subject"){

                    }
                }
            }
        }
        //configureRouting()
    }.start(wait = true)
}
