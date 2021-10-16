package br.caos.models

import java.util.*

class User (
    val Id: Int,
    val username: String,
    val password: String,
    val code: String,
    val fullName: String,
    val roleId: Int,
    val creationTime: Date
)