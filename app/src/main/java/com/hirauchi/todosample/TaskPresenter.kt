package com.hirauchi.todosample

class TaskPresenter(private val taskRepository: TaskRepository, private val view: TaskContract.View): TaskContract.Presenter {

    override fun loadTasks() {
        taskRepository.loadTasks(object : TaskDataSource.LoadTaskCallback {
            override fun onLoadTasks(tasks: List<Task>) {
                view.onLoadTasks(tasks)
            }

            override fun onError(error: Throwable) {
                view.showError(error.message)
            }
        })
    }

    override fun insertTask(description: String) {
        val task = Task(0, 0, description)

        taskRepository.insertTask(task, object : TaskDataSource.Callback {
            override fun onSuccess() {
                loadTasks()
            }

            override fun onError(error: Throwable) {
                view.showError(error.message)
            }
        })
    }

    override fun updateTaskState(task: Task) {
        task.state++
        if (task.state > 2) task.state = 0

        taskRepository.updateTask(task, object : TaskDataSource.Callback {
            override fun onSuccess() {
                loadTasks()
            }

            override fun onError(error: Throwable) {
                view.showError(error.message)
            }
        })
    }

    override fun updateTaskDescription(task: Task, description: String) {
        task.description = description

        taskRepository.updateTask(task, object : TaskDataSource.Callback {
            override fun onSuccess() {
                loadTasks()
            }

            override fun onError(error: Throwable) {
                view.showError(error.message)
            }
        })
    }

    override fun deleteTask(task: Task) {

        taskRepository.deleteTask(task, object : TaskDataSource.Callback {
            override fun onSuccess() {
                loadTasks()
            }

            override fun onError(error: Throwable) {
                view.showError(error.message)
            }
        })
    }
}