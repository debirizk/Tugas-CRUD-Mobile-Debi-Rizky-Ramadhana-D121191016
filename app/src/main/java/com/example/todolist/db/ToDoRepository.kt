package com.example.todolist.db

class ToDoRepository(private val dao: ToDoDAO) {

    val todo = dao.getAllToDo()

    suspend fun insert(todo: ToDo): Long {
        return dao.insertToDo(todo)
    }

    suspend fun update(todo: ToDo): Int {
        return dao.updateToDo(todo)
    }

    suspend fun delete(todo: ToDo): Int {
        return dao.deleteToDo(todo)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}