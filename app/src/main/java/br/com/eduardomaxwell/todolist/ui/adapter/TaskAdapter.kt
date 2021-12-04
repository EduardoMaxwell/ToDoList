package br.com.eduardomaxwell.todolist.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.eduardomaxwell.todolist.databinding.ItemTaskBinding
import br.com.eduardomaxwell.todolist.data.model.Task

class TaskAdapter(private val onTaskClicked: (Task) -> Unit) :
    ListAdapter<Task, TaskAdapter.TaskViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = getItem(position)
        holder.itemView.setOnClickListener {
            onTaskClicked(currentTask)
        }
        holder.bind(currentTask)
    }

    fun getTaskAt(position: Int): Task{
        return currentList[position]
    }
    class TaskViewHolder(binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        private val title = binding.tvTitle
        private val dateTime = binding.tvDateTime
        private val description = binding.tvDescription

        @SuppressLint("SetTextI18n")
        fun bind(task: Task) {
            title.text = task.taskTitle
            description.text = task.taskDescription
            dateTime.text = "${task.taskDate} ${task.taskHour}"
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(
                oldItem: Task,
                newItem: Task
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Task,
                newItem: Task
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
