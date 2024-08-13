package com.todolistapp
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var todoRepository: TodoRepository
    private lateinit var taskAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoRepository = TodoRepository(this)
        val etTask = findViewById<EditText>(R.id.etTask)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val lvTasks = findViewById<ListView>(R.id.lvTasks)

        taskAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        lvTasks.adapter = taskAdapter

        btnAdd.setOnClickListener {
            val task = etTask.text.toString()
            if (task.isNotEmpty()) {
                todoRepository.addTask(task)
                refreshTasks()
                etTask.text.clear()
            }
        }

        lvTasks.setOnItemLongClickListener { _, _, position, _ ->
            val taskId = todoRepository.getAllTasks()[position].id
            todoRepository.deleteTask(taskId)
            refreshTasks()
            true
        }

        lvTasks.setOnItemClickListener { _, _, position, _ ->
            val taskId = todoRepository.getAllTasks()[position].id
            todoRepository.markTaskAsCompleted(taskId)
            refreshTasks()
        }

        refreshTasks()
    }

    private fun refreshTasks() {
        val tasks = todoRepository.getAllTasks().map {
            if (it.isCompleted) "✔ ${it.task}" else it.task
        }
        taskAdapter.clear()
        taskAdapter.addAll(tasks)
    }
}
