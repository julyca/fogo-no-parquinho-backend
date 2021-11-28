package br.caos.view

import br.caos.models.Review
import br.caos.models.Subject
import kotlinx.serialization.Serializable
import java.util.*

/** Data Object usado para comunicação e representação do relacionamento entre as entidades [User] e [Review].
 * @param reviewId [Int] Identificador da avaliação relacionada.
 * @param score [Int] Score da avaliação. De 0 a 10.
 * @param feedback [String] Comentário feito pelo avaliador durante a review.
 * @param reviewerName [String] Nome de identificação do usuário que realizou a avaliação.
 * @param reviewerRole [Int] Identificador da função do usuário que realizou a avaliação
 * @param creationTime [String] Data e horário de criação da avaliação feita sobre a máteria
 */
@Serializable
class UserReviewDto (
    val reviewId: Int,
    val score: Int,
    val feedback: String,
    val reviewerName: String,
    val reviewerRole: Int,
    val creationTime: String
)