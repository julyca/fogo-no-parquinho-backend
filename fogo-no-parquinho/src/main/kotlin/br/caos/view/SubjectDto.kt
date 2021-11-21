package br.caos.view

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class SubjectDto (
    val id: Int=0,
    val code: String,
    val name: String,
    val description: String,
    val creationTime: String=""
)