package com.example.todolist.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDAO {
    @Insert
    suspend fun insertToDo(todo: ToDo) : Long

    @Update
    suspend fun updateToDo(todo: ToDo) : Int

    @Delete
    suspend fun deleteToDo(todo: ToDo) : Int

    @Query("DELETE FROM todo_data")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM todo_data")
    fun getAllToDo():Flow<List<ToDo>>
}