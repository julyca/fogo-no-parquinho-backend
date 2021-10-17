package br.caos.dao

interface GenericDAO {
    /** Método que adiciona uma nova entidade à base de dados
     * @param element elemento a ser adicionado
     * @return Retorna verdadeiro caso a entidade tenha sido adicionada com sucesso
     * */
    fun insert(element: Any) : Boolean;

    /** Método que encontra uma entidade por meio do seu Identificador
     * @param id identificador da entidade
     * @return Retorna a entidade encontrada
     * */
    fun get(id:Int) : Any;

    /** Método que lista todas as entidades, de um determinado tipo, registradas na base de dados
     * @return Retorna lista com as entidades encontradas, de um determinado tipo
     * */
    fun getAll() : List<Any>;

    /** Método que atualiza as informações de uma entidade já existente da base de dados
     * @param element elemento a ser editado
     * @return Retorna verdadeiro caso a entidade tenha sido modificada com sucesso
     * */
    fun update(element: Any) : Boolean;

    /** Método que remove uma entidade já existente da base de dados
     * @param id identificador do elemento a ser deletado
     * @return Retorna verdadeiro caso a entidade tenha sido excluída com sucesso
     * */
    fun delete(id:Int) : Boolean;

}