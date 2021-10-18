package br.caos.models

import java.util.*

class Review (
    val id: Int,
    val score: Int,
    val feedback: String,
    val reviewerId: Int,
    val creationTime: Date
)