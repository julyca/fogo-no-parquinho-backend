package br.caos.dao

import br.caos.shared.SharedPaths
import java.sql.*

class ConnectionDAO {
    private var connection : Connection
    var statement : Statement?
    var resultSet : ResultSet?
    var preparedStatement : PreparedStatement?

    init {
        this.connection = DriverManager.getConnection(SharedPaths.CONNECTION_STRING)
        this.statement = null
        this.resultSet = null
        this.preparedStatement = null
    }

    /** Método que executa uma query SQL.
     * @param query [String] query SQL a ser executada
     * @return resultado da query
     */
    fun executeQuery(query : String) : ResultSet?{
        this.statement = this.connection.createStatement()
        this.resultSet = this.statement?.executeQuery(query)
        return this.resultSet
    }

    /**
     * Método que fecha todas as conexões abertas com o banco de dado
     */
    fun close(){
        this.connection.close()
        this.statement?.close()
        this.preparedStatement?.close()
        this.resultSet?.close()
    }

    /** Método que prepara um [Statement] para uma determinada query
     * @param query [String] query SQL
     * @return o statement que deve ser utilizado.
     */
    fun getPreparedStatement(query: String): PreparedStatement? {
        this.preparedStatement = this.connection?.prepareStatement(query)
        return this.preparedStatement
    }

    /**
     * Método que executa os comandos preparados sob uma conexão
     */
    fun commit() {
        this.connection?.commit()
    }
}