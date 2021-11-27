package br.caos.view

import kotlinx.serialization.Serializable

/** Data Object usado para comunicação e representação da entidade [Review] para a aplicação que consumirá a API.
 * @param id [Int] Identificador da Entidade. (Não é obrigatório para a criação do objeto).
 * @param score [Int] Score da avaliação. De 0 a 10.
 * @param feedback [String] Comentário feito pelo avaliador durante a review.
 * @param reviewerId [Int] Identificador do usuário que realizou a avaliação. (Não é obrigatório para a criação do objeto).
 */
@Serializable
data class ReviewDto (
    val id: Int = 0, // Segundo a documentação oficial do serialization, este é um jeito correto de deixar um campo com preenchimento opcional. Para mais informações: https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/basic-serialization.md#optional-property-initializer-call
    val score: Int,
    val feedback: String,
    var reviewerId: Int = 0
)
