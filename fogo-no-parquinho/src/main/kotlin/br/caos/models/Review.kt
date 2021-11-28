package br.caos.models

import java.util.*

/** Model usado para a representação da entidade [Review] do banco de dados.
 * @param id [Int] Identificador da Entidade.
 * @param score [Int] Score da avaliação. De 0 a 10.
 * @param feedback [String] Comentário feito pelo avaliador durante a review.
 * @param reviewerId [Int] Identificador do usuário que realizou a avaliação.
 * @param creationTime [Date] Data hora da criação da avaliação
 */
class Review (
    val id: Int,
    val score: Int,
    val feedback: String,
    val reviewerId: Int,
    val creationTime: Date
)