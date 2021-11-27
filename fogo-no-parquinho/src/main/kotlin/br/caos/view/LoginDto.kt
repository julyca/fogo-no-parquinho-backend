package br.caos.view

import kotlinx.serialization.Serializable

/** Data Object usado para a transmissão dos dados de Login.
 *  @param username [String] Nome de usuário usado para a sua identificação.
 *  @param password [String] Senha do usuário. (Hasheada em SHA256)
 */
@Serializable
data class LoginDto (
    val username: String,
    val password: String
)