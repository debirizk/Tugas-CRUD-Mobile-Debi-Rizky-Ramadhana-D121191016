package com.example.todolist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_data")
data class ToDo (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_id")
    var id: Int,

    @ColumnInfo(name = "todo_name")
    var name: String,

    @ColumnInfo(name = "todo_time")
    var time: String
    )