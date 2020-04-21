package com.hirauchi.todosample

class TaskPresenter(private val taskDao: TaskDao, private val view: TaskContract.View): TaskContract.Presenter {

    override fun loadTasks() {
        val tasks = taskDao.getAll()
        view.onLoadTasks(tasks)
    }

    override fun insertTask(description: String) {
        taskDao.insert(Task(0, 0, description))
        loadTasks()
    }

    override fun updateTaskState(task: Task) {
        task.state++
        if (task.state > 2) task.state = 0
        taskDao.update(task)
        loadTasks()
    }

    override fun updateTaskDescription(task: Task, description: String) {
        task.description = description
        taskDao.update(task)
        loadTasks()
    }

    override fun deleteTask(task: Task) {
        taskDao.delete(task)
        loadTasks()
    }
}