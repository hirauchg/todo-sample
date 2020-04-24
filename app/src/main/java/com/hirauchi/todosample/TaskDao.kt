package com.hirauchi.todosample

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): Single<List<Task>>

    @Insert
    fun insert(task: Task): Completable

    @Update
    fun update(task: Task): Completable

    @Delete
    fun delete(task: Task): Completable
}
