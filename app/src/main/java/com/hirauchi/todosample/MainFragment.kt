package com.hirauchi.todosample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var dao: TaskDao
    private lateinit var adapter: TasksAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = context?: return

        val db = TaskDatabase.getInstance(context)
        dao = db.taskDao()

        val tasks = dao.getAll()

        adapter = TasksAdapter(tasks, itemListener)
        listView.adapter = adapter

        addTaskButton.setOnClickListener {
            val newTask = Task(0, 0, "New Task")
            dao.insert(newTask)

            adapter.tasks = dao.getAll()
        }
    }

    val itemListener = object : TaskItemListener {
        override fun onStateClick(task: Task) {
            Toast.makeText(activity, "onStateClick : " + task.description, Toast.LENGTH_SHORT).show()
        }

        override fun onDescriptionClick(task: Task) {
            task.description = "Updated Task"
            dao.update(task)
            adapter.tasks = dao.getAll()
        }

        override fun onDeleteClick(task: Task) {
            dao.delete(task)
            adapter.tasks = dao.getAll()
        }
    }

    private class TasksAdapter(tasks: List<Task>, private val listener: TaskItemListener): BaseAdapter() {

        var tasks: List<Task> = tasks
            set(tasks) {
                field = tasks
                notifyDataSetChanged()
            }

        private val stateTexts = listOf(R.string.todo, R.string.doing, R.string.done)
        private val stateColors = listOf(R.color.todo, R.color.doing, R.color.done)

        override fun getCount() = tasks.size

        override fun getItem(i: Int) = tasks[i]

        override fun getItemId(i: Int) = i.toLong()

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            val task = getItem(i)
            val rowView = view ?: LayoutInflater.from(viewGroup.context).inflate(R.layout.task_item, viewGroup, false)

            rowView.findViewById<TextView>(R.id.taskState).apply {
                text = context.getString(stateTexts[task.state])
                setTextColor(ContextCompat.getColor(context, stateColors[task.state]))
                setOnClickListener {
                    listener.onStateClick(task)
                }
            }

            rowView.findViewById<TextView>(R.id.taskDescription).apply {
                text = task.description
                setOnClickListener {
                    listener.onDescriptionClick(task)
                }
            }

            rowView.findViewById<ImageView>(R.id.taskDeleteButton).setOnClickListener {
                listener.onDeleteClick(task)
            }

            return rowView
        }
    }

    interface TaskItemListener {

        fun onStateClick(task: Task)

        fun onDescriptionClick(task: Task)

        fun onDeleteClick(task: Task)
    }
}