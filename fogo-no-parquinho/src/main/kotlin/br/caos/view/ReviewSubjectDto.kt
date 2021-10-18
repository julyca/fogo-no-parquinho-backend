package br.caos.view

import kotlinx.serialization.Serializable

@Serializable
data class ReviewSubjectDto(
    val subjectId : Int,
    val review : ReviewDto
)

