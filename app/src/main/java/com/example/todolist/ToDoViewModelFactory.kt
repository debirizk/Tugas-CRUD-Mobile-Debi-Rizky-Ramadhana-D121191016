package com.example.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.db.ToDoRepository
import java.lang.IllegalArgumentException

class ToDoViewModelFactory (
    private val repository: ToDoRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ToDoViewModel::class.java)){
            return ToDoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}