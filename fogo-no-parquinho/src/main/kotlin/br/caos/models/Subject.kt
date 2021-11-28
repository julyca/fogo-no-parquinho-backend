package br.caos.models

import br.caos.view.SubjectDto
import java.util.*

/** Model usado para representação da entidade [Subject] do banco de dados.
 * @param id [Int] Identificador da Entidade.
 * @param code [String] Código da Disciplina. Ex.: ECM251.
 * @param name [String] Nome da Disciplina. Ex.: Linguagens de Programação I.
 * @param description [String] Descrição breve da Disciplina. Ex.: Magia de Camponês.
 * @param creationTime [Date] Data e horário de criação da entidade.
 */
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