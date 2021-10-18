package br.caos.view

import kotlinx.serialization.Serializable

@Serializable
data class ReviewUserDto(
    val userId : Int,
    val review : ReviewDto
)

