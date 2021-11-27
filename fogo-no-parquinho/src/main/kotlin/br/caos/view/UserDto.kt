package br.caos.view

import kotlinx.serialization.Serializable

/** Data Object usado para comunicação e representação da entidade [User] para a aplicação que consumirá a API.
 *  @param id [Int] Identificador da Entidade. (Não é obrigatório para a criação do objeto).
 *  @param username [String] Nome de usuário usado para a sua identificação.
 *  @param code [String] Código de identificação do usuário. Ex: RA do Aluno.
 *  @param fullName [String] Nome completo do usuário.
 *  @param roleId [Int] Identificador da função do usuário. Ex: 1 -> aluno, 2 -> professor.
 */
@Serializable
data class UserDto (
    val id: Int=0,
    val username: String,
    val code: String,
    val fullName: String,
    val roleId: Int
)