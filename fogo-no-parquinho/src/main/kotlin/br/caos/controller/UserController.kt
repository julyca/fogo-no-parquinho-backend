package br.caos.controller

import br.caos.dao.UserDAO
import br.caos.models.User
import br.caos.view.UserDto
import java.security.MessageDigest
import java.util.*

class UserController {
    private val _userDAO : UserDAO = UserDAO()

    fun RegisterUser(dto : UserDto) : Boolean {
        var user = User(0, dto.username, dto.password, dto.code, dto.fullName, dto.roleId, Date())
        var result = true
        try {
            _userDAO.insert(user)
        }catch (ex : Exception){
            result = false
            ex.printStackTrace()
        }
        return result
    }


    fun String.md5(): String {
        return hashString(this, "MD5")
    }

    fun String.sha256(): String {
        return hashString(this, "SHA-256")
    }

    private fun hashString(input: String, algorithm: String): String {
        return MessageDigest
            .getInstance(algorithm)
            .digest(input.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }
}