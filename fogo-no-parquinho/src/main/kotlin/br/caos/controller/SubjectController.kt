package br.caos.controller

import br.caos.dao.SubjectDAO
import br.caos.models.Subject
import br.caos.models.User
import br.caos.view.SubjectDto
import br.caos.view.UserDto
import java.util.*

class SubjectController {
    private val _subjectDAO : SubjectDAO = SubjectDAO()

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

    fun listAllSubjects():List<SubjectDto>{
        val list = _subjectDAO.getAll() as List<Subject>
        var result = mutableListOf<SubjectDto>()
        for (sub in list){
            result.add(sub.toDto())
        }
        return result
    }

    fun getSubjectInfo(code : String) : UserDto?{
        return _subjectDAO.getByCode(code)?.toDto()
    }
}