package br.caos

import br.caos.controller.UserController
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import br.caos.plugins.*
import br.caos.view.LoginDto
import br.caos.view.UserDto
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

//import jdk.nashorn.internal.parser.JSONParser

fun main() {
    val userControl : UserController = UserController()
    embeddedServer(Netty, port = 4040, host = "0.0.0.0") {
        routing {
            get("/"){
                call.respondText("Hello World!")
            }
            post("/login") {
                val formParameters = call.receiveText().toString()
                val loginInfo = Json.decodeFromString<LoginDto>(formParameters)

                call.respondText("${loginInfo.username}")
            }
            post("/register"){
                val userDto = Json.decodeFromString<UserDto>(call.receiveText())
                var wasCreated = true
                try {
                    userControl.RegisterUser(userDto)
                } catch (ex : Exception){
                    wasCreated = false
                    ex.printStackTrace()
                }
                if(wasCreated)
                    call.respondText("Usuário criado com sucesso!")
                else
                    call.respondText("Deu ruim em algo")

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
