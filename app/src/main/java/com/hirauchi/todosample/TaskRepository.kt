package com.hirauchi.todosample

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class TaskRepository(private val taskDao: TaskDao): TaskDataSource {

    val disposables = CompositeDisposable()

    override fun loadTasks(callback: TaskDataSource.LoadTaskCallback) {
        taskDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    callback.onLoadTasks(it)
                },
                onError = {
                    callback.onError(it)
                }
            ).addTo(disposables)
    }

    override fun insertTask(task: Task, callback: TaskDataSource.Callback) {
        taskDao.insert(task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    callback.onSuccess()
                },
                onError = {
                    callback.onError(it)
                }
            ).addTo(disposables)
    }

    override fun updateTask(task: Task, callback: TaskDataSource.Callback) {
        taskDao.update(task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    callback.onSuccess()
                },
                onError = {
                    callback.onError(it)
                }
            ).addTo(disposables)
    }

    override fun deleteTask(task: Task, callback: TaskDataSource.Callback) {
        taskDao.delete(task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    callback.onSuccess()
                },
                onError = {
                    callback.onError(it)
                }
            ).addTo(disposables)
    }
}