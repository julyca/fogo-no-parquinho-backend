package br.caos.dao

import br.caos.models.Subject

class SubjectDAO : GenericDAO {
    override fun insert(element: Any): Boolean {
        var result: Boolean = true
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val subject = element as Subject
            val preparedStatement = connection.getPreparedStatement("""
                INSERT INTO Subject
                (code, name, description, creationTime)
                VALUES (?,?,?,NOW());
            """.trimIndent())
            preparedStatement?.setString(1,subject.code)
            preparedStatement?.setString(2,subject.name)
            preparedStatement?.setString(3,subject.description)
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
        var subject : Subject? = null
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val resultSet = connection?.executeQuery("SELECT * FROM Subject WHERE id == $id")
            while (resultSet?.next()!!)
                subject = Subject(
                    resultSet.getInt("id"),
                    resultSet.getString("code"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDate("creationTime")
                )
        } catch (ex : Exception){
            ex.printStackTrace()
        } finally {
            connection?.close()
            return subject!!
        }
    }
    override fun getAll(): List<Any> {
        val subject = mutableListOf<Subject>()
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val resultSet = connection.executeQuery("SELECT * FROM Subject;")
            while (resultSet?.next()!!){
                subject.add(Subject(
                    resultSet.getInt("id"),
                    resultSet.getString("code"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDate("creationTime")
                ))
            }
        } catch (ex : Exception){
            ex.printStackTrace()
        }
        finally {
            connection?.close()
            return subject
        }
    }
    override fun update(element: Any): Boolean {
        var connection : ConnectionDAO? = null
        var subject : Subject
        var result : Boolean = true
        try {
            connection = ConnectionDAO()
            subject = element as Subject
            val preparedStatement = connection.getPreparedStatement("""UPDATE Subject 
                SET code=?, name=?, description=?
                WHERE id=?
            """.trimMargin())
            preparedStatement?.setString(1,subject.code)
            preparedStatement?.setString(2,subject.name)
            preparedStatement?.setString(3,subject.description)
            preparedStatement?.setInt(5,subject.id)
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
            val preparedStatement = connection.getPreparedStatement("DELETE FROM Subject WHERE id =?")
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