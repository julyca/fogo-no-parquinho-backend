package br.caos

import br.caos.controller.ReviewController
import br.caos.controller.SubjectController
import br.caos.controller.UserController
import br.caos.plugins.configureRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import br.caos.shared.SharedPaths
import br.caos.view.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

fun main() {
    // Instanciando controllers das entidades
    val userControl = UserController()
    val reviewControl = ReviewController()
    val subjectControl = SubjectController()

    embeddedServer(Netty, port = 4040, host = "0.0.0.0") {
        // Configurando CORS para integração com aplicações.
        install(CORS){
            anyHost()
            method(HttpMethod.Options)
            header(HttpHeaders.Authorization)
        }
        // Configurando método de Autenticação da API. Modo escolhido: JWT TOKEN HS256
        install(Authentication){
            jwt("auth-jwt"){
               verifier(JWT.require(Algorithm.HMAC256(SharedPaths.JWT_SECRET))
                       .withAudience(SharedPaths.JWT_AUD)
                       .withIssuer(SharedPaths.JWT_ISS)
                       .build())
               // validando credenciais: username não pode ser nulo; username deve existir na base; o token não pode ter expirado
               validate { jwtCredential ->
                   if(jwtCredential.payload.getClaim("username").asString() != "" && jwtCredential.payload.expiresAt > Date.from(Instant.now()))
                       if(userControl.userExists(jwtCredential.payload.getClaim("username").asString())) // verificando se usuário do token existe
                           JWTPrincipal(jwtCredential.payload)
                       else null
                   else null
               }
            }
        }
        // Carregando configuração de rotas
        configureRouting(userControl, subjectControl, reviewControl)
    }.start(wait = true)
}
