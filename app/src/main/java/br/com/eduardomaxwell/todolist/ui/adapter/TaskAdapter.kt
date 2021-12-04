package br.com.eduardomaxwell.todolist.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.eduardomaxwell.todolist.R
import br.com.eduardomaxwell.todolist.data.model.Priority
import br.com.eduardomaxwell.todolist.databinding.ItemTaskBinding
import br.com.eduardomaxwell.todolist.data.model.Task
import br.com.eduardomaxwell.todolist.databinding.NewItemTaskBinding

class TaskAdapter(private val onTaskClicked: (Task) -> Unit) :
    ListAdapter<Task, TaskAdapter.TaskViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            NewItemTaskBinding.inflate(
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

    fun getTaskAt(position: Int): Task {
        return currentList[position]
    }

    class TaskViewHolder(binding: NewItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        private val title = binding.tvTitle
        private val dateTime = binding.tvDateTime
        private val description = binding.tvDescription
        private val priority = binding.prioridadeColor

        private val context = binding.root.context

        @SuppressLint("SetTextI18n")
        fun bind(task: Task) {
            title.text = task.taskTitle
            description.text = task.taskDescription
            dateTime.text = "${task.taskDate} ${task.taskHour}"

            when (task.priority) {
                Priority.HIGH -> priority.setBackgroundColor(ContextCompat.getColor(context, R.color.high_priority))
                Priority.MEDIUM -> priority.setBackgroundColor(ContextCompat.getColor(context, R.color.medium_priority))
                Priority.LOW -> priority.setBackgroundColor(ContextCompat.getColor(context, R.color.low_priority))
            }
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
