package br.caos.dao

import br.caos.shared.SharedPaths
import java.sql.*

class ConnectionDAO {
    var connection : Connection
    var statement : Statement?
    var resultSet : ResultSet?
    var preparedStatement : PreparedStatement?

    init {
        this.connection = DriverManager.getConnection(SharedPaths.CONNECTION_STRING)
        this.statement = null
        this.resultSet = null
        this.preparedStatement = null
    }

    fun executeQuery(query : String) : ResultSet?{
        this.statement = this.connection.createStatement()
        this.resultSet = this.statement?.executeQuery(query)
        return this.resultSet
    }

    fun close(){
        this.connection.close()
        this.statement?.close()
        this.preparedStatement?.close()
        this.resultSet?.close()
    }

    fun getPreparedStatement(query: String): PreparedStatement? {
        this.preparedStatement = this.connection?.prepareStatement(query)
        return this.preparedStatement
    }

    fun commit() {
        this.connection?.commit()
    }
}