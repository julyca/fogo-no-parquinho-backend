package br.caos.view

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class UserReviewDto (
    val reviewId: Int,
    val score: Int,
    val feedback: String,
    val reviewerName: String,
    val reviewerRole: Int,
    val creationTime: String
)