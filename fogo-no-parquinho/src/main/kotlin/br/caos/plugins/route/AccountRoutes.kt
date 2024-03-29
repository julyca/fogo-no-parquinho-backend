package br.caos.plugins.route

import br.caos.controller.UserController
import br.caos.shared.SharedPaths
import br.caos.view.LoginDto
import br.caos.view.UserDto
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.util.*

/*** Método que configura rota de Login do Usuário, permitindo o seu acesso a zonas protegidas da API.
 *  Recebe JSON Body com nome de usuário (username) e senha (password, deve ser enviada em Hash)
 *  @param userControl referência ao controller de usuário para efetuar as operações
 *  @return 200: Token que deve ser usado para autenticação do usuário;
 *  401: texto informando que as credenciais são inválidas;
 *  400: texto informando que o JSON enviado está no formato incorreto.
 */
fun Route.loginRoute(userControl : UserController) {
    post("/login") {
        try {
            println("[${LocalDateTime.now()}] POST login") // Exibindo quando o método foi chamado para fins de Log
            val loginInfo = Json.decodeFromString<LoginDto>(call.receiveText()) // Adquirindo dados da requisição
            val wasLoginSuccessful = userControl.login(loginInfo) // Validando os dados recebidos
            if (wasLoginSuccessful){ // Se os dados estão corretos (login valido), gerar token de autenticação
                val token = JWT.create()
                    .withAudience(SharedPaths.JWT_AUD)
                    .withIssuer(SharedPaths.JWT_ISS)
                    .withClaim("username", loginInfo.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 90000000)) // OBS: Por questões de segurança, o tempo de expiração é de 15 min, MAS por questão de praticidade no estudo vai ser de 25 horas ( ❛ ᴗ ❛.)
                    .sign(Algorithm.HMAC256(SharedPaths.JWT_SECRET))
                call.respond(HttpStatusCode.OK,"{ \"token\" : \"$token\"}") // devolvendo token para o front-end
            } else {
                call.respond(HttpStatusCode.Unauthorized,"Nome de usuário ou senha inválidos.")
            }

        } catch (ex: Exception){
            ex.printStackTrace()
            call.respond(HttpStatusCode.BadRequest,"O formato dos dados enviados está incorreto.")
        }
    }
}

/*** Método que configura rota de Cadastro do Usuário, permitindo o seu registro no sistema.
 *  Recebe JSON Body com nome de usuário (username), senha (password, deve ser enviada em Hash),
 *  código (code), nome completo (fullName) e tipo de perfil (roleId)
 *  @param userControl referência ao controller de usuário para efetuar as operações
 *  @return 200: texto informando registro efetuado com sucesso;
 *  500: texto informando erro ao realizar o registro;
 *  400: texto informando que o JSON enviado está no formato incorreto.
 */
fun Route.createAccountRoute(userControl : UserController) {
    post("/register") {
        try {
            println("[${LocalDateTime.now()}] POST register") // Exibindo quando o método foi chamado para fins de Log
            val userDto = Json.decodeFromString<UserDto>(call.receiveText()) // Adquirindo dados da requisição
            val wasCreated = userControl.registerUser(userDto)
            if (wasCreated) // Se usuário foi cadastrado com sucesso
                call.respondText("Usuário criado com sucesso!")
            else
                call.respond(HttpStatusCode.InternalServerError, "Ocorreu um erro ao tentar cadastrar o usuário")
        } catch (ex: Exception) {
            ex.printStackTrace()
            call.respond(HttpStatusCode.BadRequest,"O formato dos dados enviados está incorreto.")
        }
    }
}