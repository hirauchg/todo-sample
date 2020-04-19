package com.hirauchi.todosample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addTaskButton.setOnClickListener {
            Toast.makeText(activity, "Add Tapped", Toast.LENGTH_SHORT).show()
        }

        // TODO ダミーデータ
        val tasks = arrayListOf<Task>()
        val task1 = Task(0, 0, "Task1")
        val task2 = Task(1, 0, "Task2")
        val task3 = Task(2, 1, "Task3")
        val task4 = Task(3, 2, "Task4")
        val task5 = Task(4, 2, "Task5")
        tasks.add(task1)
        tasks.add(task2)
        tasks.add(task3)
        tasks.add(task4)
        tasks.add(task5)

        listView.adapter = TasksAdapter(tasks)
    }

    private class TasksAdapter(private val tasks: List<Task>): BaseAdapter() {

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
            }

            rowView.findViewById<TextView>(R.id.taskDescription).apply {
                text = task.description
            }
            return rowView
        }
    }
}