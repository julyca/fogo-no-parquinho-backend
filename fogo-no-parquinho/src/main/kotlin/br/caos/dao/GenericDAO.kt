package br.caos.dao

interface GenericDAO {
    fun insert(element: Any) : Boolean;
    fun get(id:Int) : Any;
    fun getAll() : List<Any>;
    fun update(element: Any) : Boolean;
    fun delete(id:Int) : Boolean;

}