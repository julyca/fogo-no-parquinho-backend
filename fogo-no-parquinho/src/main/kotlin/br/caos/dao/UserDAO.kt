package br.caos.dao

import br.caos.models.User
import br.caos.models.UserRoles
import br.caos.view.RoleDto

class UserDAO : GenericDAO {
    override fun insert(element: Any): Boolean {
        var result: Boolean = true
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val user = element as User
            val preparedStatement = connection.getPreparedStatement("""
                INSERT INTO User
                (username, password, code, fullName, roleId, creationTime)
                VALUES (?,?,?,?,?,NOW());
            """.trimIndent())
            preparedStatement?.setString(1,user.username)
            preparedStatement?.setString(2,user.password)
            preparedStatement?.setString(3,user.code)
            preparedStatement?.setString(4,user.fullName)
            preparedStatement?.setInt(5,user.roleId)
            preparedStatement?.executeUpdate()
            connection.commit()
        } catch (ex : Exception){
            result = false
            ex.printStackTrace()
        } finally {
            connection?.close()
            return result
        }
    }

    override fun get(id: Int): Any {
        var user : User? = null
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val resultSet = connection?.executeQuery("SELECT * FROM User WHERE id == $id")
            while (resultSet?.next()!!)
                user = User(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    "",
                    resultSet.getString("code"),
                    resultSet.getString("fullName"),
                    resultSet.getInt("roleId"),
                    resultSet.getDate("creationTime"))
        } catch (ex : Exception){
            ex.printStackTrace()
        } finally {
            connection?.close()
            return user!!
        }
    }

    override fun getAll(): List<Any> {
        val users = mutableListOf<User>()
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val resultSet = connection.executeQuery("SELECT * FROM User;")
            while (resultSet?.next()!!){
                users.add(User(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    "",
                    resultSet.getString("code"),
                    resultSet.getString("fullName"),
                    resultSet.getInt("roleId"),
                    resultSet.getDate("creationTime")
                ))
            }
        } catch (ex : Exception){
            ex.printStackTrace()
        }
        finally {
            connection?.close()
            return users
        }
    }

    override fun update(element: Any): Boolean {
        var connection : ConnectionDAO? = null
        var user : User
        var result : Boolean = true
        try {
            connection = ConnectionDAO()
            user = element as User
            val preparedStatement = connection.getPreparedStatement("""UPDATE User 
                SET username=?, password=?, fullName=?, code=?
                WHERE id=?
            """.trimMargin())
            preparedStatement?.setString(1,user.username)
            preparedStatement?.setString(2,user.password)
            preparedStatement?.setString(3,user.fullName)
            preparedStatement?.setString(4,user.code)
            preparedStatement?.setInt(5,user.id)
            preparedStatement?.executeUpdate()
            connection.commit()
        } catch (ex : Exception){
            ex.printStackTrace()
            result = false
        } finally {
            connection?.close()
            return result
        }
    }

    override fun delete(id: Int): Boolean {
        var connection : ConnectionDAO? = null
        var result : Boolean = true
        try {
            connection = ConnectionDAO()
            val preparedStatement = connection.getPreparedStatement("DELETE FROM User WHERE id ==?")
            preparedStatement?.setInt(1,id)
            preparedStatement?.executeUpdate()
            connection.commit()
        } catch (ex : Exception){
            ex.printStackTrace()
            result = false
        } finally {
            connection?.close()
            return result
        }
    }

    /** Método que procura por uma entidade [User] dado seu Username
     * @param username Nome do Usuário que será procurado
     * @return Usuário encontrado
     * */
    fun getByUsername(username:String) : User? {
        var user : User? = null
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val resultSet = connection?.executeQuery("SELECT * FROM User WHERE username like \"$username\"")
            while (resultSet?.next()!!)
                user = User(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    "",
                    resultSet.getString("code"),
                    resultSet.getString("fullName"),
                    resultSet.getInt("roleId"),
                    resultSet.getDate("creationTime"))
        } catch (ex:Exception) {
            ex.printStackTrace()
        } finally {
            connection?.close()
            return user
        }
    }

    /** Método que procura por uma entidade [User] dado seu Código (Code)
     * @param code Código do Usuário que será procurado
     * @return Usuário encontrado
     * */
    fun getByCode(code:String) : User? {
        var user : User? = null
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val resultSet = connection?.executeQuery("SELECT * FROM User WHERE code like \"$code\"")
            while (resultSet?.next()!!)
                user = User(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    "",
                    resultSet.getString("code"),
                    resultSet.getString("fullName"),
                    resultSet.getInt("roleId"),
                    resultSet.getDate("creationTime"))
        } catch (ex:Exception) {
            ex.printStackTrace()
        } finally {
            connection?.close()
            return user
        }
    }

    /** Método que lista todas as funções cadastradas de um usuário
     * @return lista com todas as funções que um usuário pode assumir como um objeto de [UserRoles]
     */
    fun getRoles(): List<UserRoles>{
        val roles = mutableListOf<UserRoles>()
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val resultSet = connection.executeQuery("SELECT * FROM UserRoles;")
            while (resultSet?.next()!!){
                roles.add(UserRoles(resultSet.getInt("id"), resultSet.getString("roleName")))
            }
        } catch (ex : Exception){
            ex.printStackTrace()
        }
        finally {
            connection?.close()
            return roles
        }
    }

    /** Método que relaciona um usuário a uma matéria.
     * Se o usuário for um ALUNO tem-se que: Aluno cursa a máteria, se o usuário for um PROFESSOR tem-se que: Professor ministra a matéria.
     * @param subId [Int] Identificador da disciplina que será relacionada.
     * @param userId [Int] Identificador do usuário que será relacionado.
     * @return Falso somente em caso de erro durante a criação do relacionamento.
     */
    fun relateSubject(subId : Int, userId: Int) : Boolean {
        var result = true
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val preparedStatement = connection.getPreparedStatement("""
                INSERT INTO UserSubjects
                (userId, subjectId)
                VALUES (?,?);
            """.trimIndent())
            preparedStatement?.setInt(2,subId)
            preparedStatement?.setInt(1,userId)

            preparedStatement?.executeUpdate()
            connection.commit()
        } catch (ex : Exception){
            result = false
            ex.printStackTrace()
        } finally {
            connection?.close()
            return result
        }
    }
}