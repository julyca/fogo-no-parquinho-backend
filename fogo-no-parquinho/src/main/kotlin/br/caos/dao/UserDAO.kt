package br.caos.dao

import br.caos.models.User

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
            val resultSet = connection?.executeQuery("SELECT * FROM produtos WHERE id == $id")
            while (resultSet?.next()!!)
                user = User(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("code"),
                    resultSet.getString("fullName"),
                    resultSet.getInt("roleId"),
                    resultSet.getDate("creationTime")
                )
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
            val preparedStatement = connection.getPreparedStatement("DELETE FROM User WHERE id =?")
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

}