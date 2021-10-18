package br.caos.view

import kotlinx.serialization.Serializable

@Serializable
data class RoleDto (
    val id: Int,
    val roleName: String
)