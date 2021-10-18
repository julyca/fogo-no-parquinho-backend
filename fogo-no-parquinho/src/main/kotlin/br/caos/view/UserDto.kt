package br.caos.view

import kotlinx.serialization.Serializable

@Serializable
data class UserDto (
    val id: Int=0,
    val username: String,
    val password: String,
    val code: String,
    val fullName: String,
    val roleId: Int
)