package br.caos.models

import br.caos.view.SubjectDto
import java.util.*

class Subject (
    val id: Int,
    val code: String,
    val name: String,
    val description: String,
    val creationTime: Date
) {
    fun toDto(): SubjectDto = SubjectDto(
        id,
        code,
        name,
        description,
        creationTime.toString()
    )
}