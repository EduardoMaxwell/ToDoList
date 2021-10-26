package br.com.eduardomaxwell.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.eduardomaxwell.todolist.TaskApplication
import br.com.eduardomaxwell.todolist.databinding.ActivityMainBinding
import br.com.eduardomaxwell.todolist.ui.viewmodel.TaskViewModel
import br.com.eduardomaxwell.todolist.ui.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    private val newTaskActivityRequestCode = 1
    private lateinit var binding: ActivityMainBinding
    private val taskAdapter = TaskAdapter()
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycler()
        setListeners()

        taskViewModel.allTasks.observe(this, { tasks ->
            tasks?.let {
                if (it.isEmpty()) {
//                    binding.includeEmpty.emptyState.visibility = View.VISIBLE
                } else {
//                    binding.includeEmpty.emptyState.visibility = View.GONE
                }
                taskAdapter.submitList(it)
            }
        })
    }

    private fun setListeners() {
        binding.fabAddTask.setOnClickListener {
            val intent = AddTaskActivity.getStartIntent(this@MainActivity)
            this@MainActivity.startActivityForResult(intent, newTaskActivityRequestCode)
        }

        taskAdapter.listenerEdit = {
            val intent = AddTaskActivity.getStartIntent(this@MainActivity)
            this@MainActivity.startActivityForResult(intent, AddTaskActivity.CREATE_NEW_TASK)
            setupRecycler()
        }

        taskAdapter.listenerDelete = {
            taskViewModel.delete(it)
            setupRecycler()
        }

        binding.rvTasks.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && binding.fabAddTask.visibility == View.VISIBLE) {
                    binding.fabAddTask.hide()
                } else if (dy < 0 && binding.fabAddTask.visibility != View.VISIBLE) {
                    binding.fabAddTask.show()
                }
            }
        })
    }

    private fun setupRecycler() {

        binding.rvTasks.run {
            setHasFixedSize(true)
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newTaskActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(AddTaskActivity.EXTRA_TITLE)?.let { reply ->
                taskViewModel.insert(reply)
            }

        } else {
            Toast.makeText(
                applicationContext,
                "Empty not saved",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}