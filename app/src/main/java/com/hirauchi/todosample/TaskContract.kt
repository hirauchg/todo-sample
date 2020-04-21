package com.hirauchi.todosample

interface TaskContract {

    interface View {

        fun onLoadTasks(tasks: List<Task>)
    }

    interface Presenter {

        fun loadTasks()

        fun insertTask(description: String)

        fun updateTaskState(task: Task)

        fun updateTaskDescription(task: Task, description: String)

        fun deleteTask(task: Task)
    }
}
