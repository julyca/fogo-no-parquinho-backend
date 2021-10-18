package br.caos.dao

import br.caos.models.User
import br.caos.models.UserRoles

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
                    resultSet.getString("password"),
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
                    resultSet.getString("password"),
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

    /** Método que procura por uma entidade Usuário dado seu Username
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
                    resultSet.getString("password"),
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

    /** Método que procura por uma entidade Usuário dado seu Código (Code)
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
                    resultSet.getString("password"),
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
}