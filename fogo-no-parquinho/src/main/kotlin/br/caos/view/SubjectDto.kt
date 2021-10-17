package br.caos.view

import kotlinx.serialization.Serializable

@Serializable
data class SubjectDto (
    val id: Int,
    val code: String,
    val name: String,
    val description: String
)