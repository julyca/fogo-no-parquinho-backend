package br.caos.models

import java.util.*

data class User (
    val id: Int,
    val username: String,
    val password: String,
    val code: String,
    val fullName: String,
    val roleId: Int,
    val creationTime: Date
)
