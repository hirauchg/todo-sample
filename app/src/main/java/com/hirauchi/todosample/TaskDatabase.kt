package com.hirauchi.todosample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {

        private var INSTANCE: TaskDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): TaskDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TaskDatabase::class.java, "Task.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}