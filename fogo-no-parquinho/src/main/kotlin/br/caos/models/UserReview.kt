package br.caos.models

import br.caos.view.UserReviewDto
import java.util.*

/** Model usado para representação do relacionamento entre as entidades [User] e [Review] do banco de dados.
 * @param reviewId [Int] Identificador da avaliação relacionada.
 * @param score [Int] Score da avaliação. De 0 a 10.
 * @param feedback [String] Comentário feito pelo avaliador durante a review.
 * @param reviewerName [String] Nome de identificação do usuário que realizou a avaliação.
 * @param reviewerRole [Int] Identificador da função do usuário que realizou a avaliação
 * @param creationTime [Date] Data e horário de criação da avaliação feita sobre o usuário
 */
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