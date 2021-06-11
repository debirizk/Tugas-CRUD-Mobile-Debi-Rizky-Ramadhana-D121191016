package com.example.todolist

import android.util.Patterns
import androidx.lifecycle.*
import com.example.todolist.db.ToDo
import com.example.todolist.db.ToDoRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ToDoViewModel (private val repository: ToDoRepository) : ViewModel() {
    private var isUpdateOrDelete = false
    private lateinit var todoToUpdateOrDelete: ToDo
    val inputToDo = MutableLiveData<String>()
    val inputTime = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Add Task"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun initUpdateAndDelete(todo: ToDo) {
        inputToDo.value = todo.name
        inputTime.value = todo.time
        isUpdateOrDelete = true
        todoToUpdateOrDelete = todo
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    fun saveOrUpdate() {
        if (inputToDo.value == null) {
            statusMessage.value = Event("Enter task")
        } else if (inputTime.value == null) {
            statusMessage.value = Event("Enter note")
        }
//        else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
//            statusMessage.value = Event("Please enter a correct email address")
         else {
            if (isUpdateOrDelete) {
                todoToUpdateOrDelete.name = inputToDo.value!!
                todoToUpdateOrDelete.time = inputTime.value!!
                updateToDo(todoToUpdateOrDelete)
            } else {
                val name = inputToDo.value!!
                val email = inputTime.value!!
                insertToDo(ToDo(0, name, email))
                inputToDo.value = ""
                inputTime.value = ""
            }
        }
    }

    private fun insertToDo(todo: ToDo) = viewModelScope.launch {
        val newRowId = repository.insert(todo)
        if (newRowId > -1) {
            statusMessage.value = Event("Task Inserted Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }


    private fun updateToDo(todo: ToDo) = viewModelScope.launch {
        val noOfRows = repository.update(todo)
        if (noOfRows > 0) {
            inputToDo.value = ""
            inputTime.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Add Task"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRows Row Updated Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun getSavedToDo() = liveData {
        repository.todo.collect {
            emit(it)
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            deleteToDo(todoToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    private fun deleteToDo(todo: ToDo) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(todo)
        if (noOfRowsDeleted > 0) {
            inputToDo.value = ""
            inputTime.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Add task"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    private fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Task(s) Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }
}