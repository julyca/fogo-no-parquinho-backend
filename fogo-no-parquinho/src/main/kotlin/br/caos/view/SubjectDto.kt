package br.caos.view

import kotlinx.serialization.Serializable
import java.util.*

/** Data Object usado para comunicação e representação da entidade [Subject] para a aplicação que consumirá a API.
 * @param id [Int] Identificador da Entidade. (Não é obrigatório para a criação do objeto).
 * @param code [String] Código da Disciplina. Ex.: ECM251.
 * @param name [String] Nome da Disciplina. Ex.: Linguagens de Programação I.
 * @param description [String] Descrição breve da Disciplina. Ex.: Magia de Camponês.
 * @param creationTime [String] Data e horário de criação da Entidade.
 */
@Serializable
data class SubjectDto (
    val id: Int=0,
    val code: String,
    val name: String,
    val description: String,
    val creationTime: String=""
)