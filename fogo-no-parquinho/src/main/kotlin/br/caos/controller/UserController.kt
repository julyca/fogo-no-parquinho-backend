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
     * @param dto [UserDto] Entidade que contém os dados necessários para o cadastro do Usuário. Ex: nome, senha, nome de usuário, etc.
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
     * @param dto [LoginDto] Entidade que contém as credenciais necessárias para validar o login: Username e Senha
     * @return Retorna Verdadeiro APENAS se o login for válido
     * */
    fun login(dto: LoginDto) : Boolean{
        var result = false
        val user = _userDAO.getByUsername(dto.username)
        if(user != null)
            result = user.password.equals(dto.password)
        return result
    }

    /** Método que verifica a partir do seu username a existência de um usuário
     * @param username [String] Nome de usuário usado para a sua identificação
     * @return Retorna Verdadeiro SOMENTE SE o usuário existir
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

    /** Método que resgata os dados de um usuário por meio do seu apelido
     * @param username [String] Nome de usuário usado para a sua identificação
     * @return detalhes do usuário como objeto [User]
     */
    fun getUserByName(username:String):User?{
        try {
            return _userDAO.getByUsername(username)
        }catch (ex: Exception){
            ex.printStackTrace()
        }
        return null
    }

    /** Método que lista todos os usuários cadastrados.
     * @return lista todos os usuários
     */
    fun listAllUsers():List<UserDto>{
        val list = _userDAO.getAll() as List<User>
        var result = mutableListOf<UserDto>()
        for (user in list){
            result.add(user.toDto())
        }
        return result
    }

    /** Método que lista todas as funções de um usuário
     * @return lista com todas as funções que um usuário pode assumir como um objeto de [RoleDto]
     */
    fun listAllRoles():List<RoleDto>{
        val list = _userDAO.getRoles() as List<UserRoles>
        var result = mutableListOf<RoleDto>()
        for (user in list){
            result.add(user.toDto())
        }
        return result
    }

    /** Método que resgata os dados de um usuário por meio do seu código.
     * @param code [String] Código do usuário usado para a sua identificação
     * @return detalhes do usuário como objeto [UserDto]
     */
    fun getUserInfo(code : String) : UserDto?{
        return _userDAO.getByCode(code)?.toDto()
    }

    /** Método que relaciona um usuário a uma matéria.
     * Se o usuário for um ALUNO tem-se que: Aluno cursa a máteria, se o usuário for um PROFESSOR tem-se que: Professor ministra a matéria.
     * @param subjectId [Int] Identificador da disciplina que será relacionada.
     * @param userId [Int] Identificador do usuário que será relacionado.
     * @return Falso somente em caso de erro durante a criação do relacionamento.
     */
    fun relateSubject(subjectId:Int, userId:Int):Boolean {
        return _userDAO.relateSubject(subjectId,userId)
    }




}