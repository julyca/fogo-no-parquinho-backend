package br.caos.models

import br.caos.view.UserDto
import java.util.*

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

