package br.caos.models

import br.caos.view.UserReviewDto
import java.util.*

class UserReview (
    val reviewId: Int,
    val score: Int,
    val feedback: String,
    val reviewerName: String,
    val reviewerRole: Int,
    val creationTime: Date
) {
    fun toDto(): UserReviewDto = UserReviewDto(
        reviewId,
        score,
        feedback,
        reviewerName,
        reviewerRole,
        creationTime.toString()
    )
}