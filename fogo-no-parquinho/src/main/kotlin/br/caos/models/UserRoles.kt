package br.caos.models

import br.caos.view.RoleDto

data class UserRoles (
    val id: Int,
    val roleName: String
) {
    fun toDto(): RoleDto = RoleDto(
        id,
        roleName
    )
}