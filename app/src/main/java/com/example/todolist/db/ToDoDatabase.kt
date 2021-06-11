package com.example.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ToDo::class], version = 1)
abstract class ToDoDatabase : RoomDatabase() {
    abstract val ToDoDAO: ToDoDAO

    companion object {
        @Volatile
        private var INSTANCE: ToDoDatabase? = null
        fun getInstance(context: Context): ToDoDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ToDoDatabase::class.java,
                        "person_data"
                    ).build()
                }
                return instance
            }
        }
    }
}