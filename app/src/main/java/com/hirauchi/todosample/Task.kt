package com.hirauchi.todosample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
        @PrimaryKey(autoGenerate = true)
        val id: Int,

        @ColumnInfo(name = "state")
        var state: Int,

        @ColumnInfo(name = "description")
        var description: String
)
