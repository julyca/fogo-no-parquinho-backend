package br.caos.models

import br.caos.view.UserDto
import java.util.*

/** Model usado para representação da entidade [User] do banco de dados.
 *  @param id [Int] Identificador da Entidade.
 *  @param username [String] Nome de usuário usado para a sua identificação.
 *  @param password [String] Senha do usuário. (Hasheada em SHA256).
 *  @param code [String] Código de identificação do usuário. Ex: RA do Aluno.
 *  @param fullName [String] Nome completo do usuário.
 *  @param roleId [Int] Identificador da função do usuário. Ex: 1 -> aluno, 2 -> professor.
 *  @param creationTime [Date] Data Hora de criação da entidade na base de dados.
 */
data class User (
    val id: Int,
    val username: String,
    val password: String,
    val code: String,
    val fullName: String,
    val roleId: Int,
    val creationTime: Date
) {
    fun toDto(): UserDto  = UserDto(
        id,
        username,
        password,
        code,
        fullName,
        roleId
    )
}

