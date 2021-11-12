package br.caos.models

import br.caos.view.SubjectReviewDto
import java.util.*

class SubjectReview (
    val reviewId: Int,
    val score: Int,
    val feedback: String,
    val reviewerName: String,
    val reviewerRole: Int,
    val creationTime: Date
) {
    fun toDto(): SubjectReviewDto = SubjectReviewDto(
        reviewId,
        score,
        feedback,
        reviewerName,
        reviewerRole,
        creationTime.toString()
    )
}