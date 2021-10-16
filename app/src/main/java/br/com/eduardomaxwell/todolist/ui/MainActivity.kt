package br.com.eduardomaxwell.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isEmpty
import br.com.eduardomaxwell.todolist.databinding.ActivityMainBinding
import br.com.eduardomaxwell.todolist.datasource.TaskDatasource
import br.com.eduardomaxwell.todolist.ui.AddTaskActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val taskAdapter by lazy { TaskAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecycler()
        setListeners()
    }

    private fun setListeners() {
        binding.fabAddTask.setOnClickListener {
            val intent = AddTaskActivity.getStartIntent(this@MainActivity)
            this@MainActivity.startActivityForResult(intent, AddTaskActivity.CREATE_NEW_TASK)
        }

        taskAdapter.listenerEdit = {
            val intent = AddTaskActivity.getStartIntent(this@MainActivity)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            this@MainActivity.startActivityForResult(intent, AddTaskActivity.CREATE_NEW_TASK)
            setupRecycler()
        }

        taskAdapter.listenerDelete = {
            TaskDatasource.deleteTask(it)
            setupRecycler()
        }
    }

    private fun setupRecycler() {
        binding.rvTasks.run {
            setHasFixedSize(true)
            adapter = taskAdapter

            binding.includeEmpty.emptyState.visibility = if (isEmpty()) View.VISIBLE else View.GONE

            taskAdapter.submitList(TaskDatasource.getList())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddTaskActivity.CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) {
            setupRecycler()
        }
    }
}