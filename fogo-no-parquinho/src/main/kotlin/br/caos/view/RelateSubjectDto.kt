package br.caos.view

import kotlinx.serialization.Serializable

/** Data Object usado para registrar relacionamento entre um usuário e uma disciplina.
 *  @param subjectId [Int] Identificador da disciplina que deve ser relacionada.
 */
@Serializable
data class RelateSubjectDto (
    val subjectId : Int
)