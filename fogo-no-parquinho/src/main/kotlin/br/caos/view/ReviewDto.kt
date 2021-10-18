package br.caos.view

import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto (
    val id: Int,
    val score: Int,
    val feedback: String,
    var reviewerId: Int
)
