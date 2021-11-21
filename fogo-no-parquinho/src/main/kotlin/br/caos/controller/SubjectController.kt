package br.caos.controller

import br.caos.dao.SubjectDAO
import br.caos.models.Subject
import br.caos.models.User
import br.caos.view.SubjectDto
import br.caos.view.UserDto
import java.util.*

class SubjectController {
    private val _subjectDAO : SubjectDAO = SubjectDAO()

    /** Método que realiza a criação de uma Materia (Subject)
     * @param dto Entidade que contém os dados necessários para o cadastro do Materia. Ex: codigo, nome, descriçao, etc.
     * @return Retorna Falso somente em caso de erro durante a criação da entidade.
     * */
    fun registerSubject(dto : SubjectDto) : Boolean {
        var subject = Subject(0, dto.code, dto.name, dto.description, Date())
        var result = true
        try {
            _subjectDAO.insert(subject)
        }catch (ex : Exception){
            result = false
            ex.printStackTrace()
        }
        return result
    }

    /**lista todas as Materias
     * @return lista todas as Materias
     */
    fun listAllSubjects():List<SubjectDto>{
        val list = _subjectDAO.getAll() as List<Subject>
        var result = mutableListOf<SubjectDto>()
        for (sub in list){
            result.add(sub.toDto())
        }
        return result
    }
    fun getSubjectInfo(code : String) : SubjectDto?{
        return _subjectDAO.getByCode(code)?.toDto()
    }
}