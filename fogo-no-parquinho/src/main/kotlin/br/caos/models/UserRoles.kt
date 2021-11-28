package br.caos.models

import br.caos.view.RoleDto

/** Model usado para a representação da entidade [UserRoles] do banco de dados.
 * @param id [Int] Identificador da Entidade.
 * @param roleName [String] Nome da função. Ex: professor.
 */
data class UserRoles (
    val id: Int,
    val roleName: String
) {
    fun toDto(): RoleDto = RoleDto(
        id,
        roleName
    )
}