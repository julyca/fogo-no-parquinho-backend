package br.caos.controller

import br.caos.dao.SubjectDAO
import br.caos.models.Subject
import br.caos.view.SubjectDto
import java.security.MessageDigest
import java.util.*

class SubjectController {
    private val _subjectDAO : SubjectDAO = SubjectDAO()

    fun RegisterSubject(dto : SubjectDto) : Boolean {
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
}