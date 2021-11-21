package br.caos.controller

import br.caos.dao.UserDAO
import br.caos.models.User
import br.caos.models.UserRoles
import br.caos.view.LoginDto
import br.caos.view.RoleDto
import br.caos.view.UserDto
import java.util.*

class UserController {
    private val _userDAO : UserDAO = UserDAO()

    /** Método que realiza a criação de um Usuário (User)
     * @param dto Entidade que contém os dados necessários para o cadastro do Usuário. Ex: nome, senha, nome de usuário, etc.
     * @return Retorna Falso somente em caso de erro durante a criação da entidade.
     * */
    fun registerUser(dto : UserDto) : Boolean {
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

    /** Método que valida as credências informadas
     * @param dto Entidade que contém as credências necessárias para validar o login: Username e Senha
     * @return Retorna Verdadeiro APENAS se o login for válido
     * */
    fun login(dto: LoginDto) : Boolean{
        var result = false
        val user = _userDAO.getByUsername(dto.username)
        if(user != null)
            result = user.password.equals(dto.password)
        return result
    }

    /** Método que verifica a partir de seu Username a existência de um usuário
     * @param username Nome do Usuário
     * @return Retorna Verdadeiro SOMONTE SE o usuário existir
     * */
    fun userExists(username:String):Boolean{
        var result = false
        try {
            val user = _userDAO.getByUsername(username)
            if(user != null)
                result = true
        }catch (ex: Exception){
            ex.printStackTrace()
        }
        return result
    }

    /**busca usuario pelo apelido
     * @return
     */
    fun getUserByName(username:String):User?{
        try {
            return _userDAO.getByUsername(username)
        }catch (ex: Exception){
            ex.printStackTrace()
        }
        return null
    }

    /**Lista todos os usuarios
     * @return lista todos os usuarios
     */
    fun listAllUsers():List<UserDto>{
        val list = _userDAO.getAll() as List<User>
        var result = mutableListOf<UserDto>()
        for (user in list){
            result.add(user.toDto())
        }
        return result
    }

    /**Lista todos as função
     * @return lista todos os função
     */
    fun listAllRoles():List<RoleDto>{
        val list = _userDAO.getRoles() as List<UserRoles>
        var result = mutableListOf<RoleDto>()
        for (user in list){
            result.add(user.toDto())
        }
        return result
    }
    fun getUserInfo(code : String) : UserDto?{
        return _userDAO.getByCode(code)?.toDto()
    }

    /**Relaciona professores com materias
     * @return a associaçao professores com materias
     */
    fun relateSubject(subjectId:Int, userId:Int):Boolean {
        return _userDAO.relateSubject(subjectId,userId)
    }




}