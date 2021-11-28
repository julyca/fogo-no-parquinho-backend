package br.caos.view

import kotlinx.serialization.Serializable

/** Data Object usado para comunicação e representação da entidade [UserRoles] para a aplicação que consumirá a API.
 * @param id [Int] Identificador da Entidade.
 * @param roleName [String] Nome da função. Ex: professor.
 */
@Serializable
data class RoleDto (
    val id: Int,
    val roleName: String
)