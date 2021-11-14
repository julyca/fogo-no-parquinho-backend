package br.caos.view

import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto (
    val id: Int = 0, // Segundo a documentação oficial do serialization, este é um jeito correto de deixar um campo com preenchimento opcional. Para mais informações: https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/basic-serialization.md#optional-property-initializer-call
    val score: Int,
    val feedback: String,
    var reviewerId: Int = 0
)
