package br.com.eduardomaxwell.todolist.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.eduardomaxwell.todolist.R
import br.com.eduardomaxwell.todolist.databinding.ItemTaskBinding
import br.com.eduardomaxwell.todolist.data.model.Task

class TaskAdapter(private val onTaskClicked: (Task) -> Unit) :
    ListAdapter<Task, TaskAdapter.TaskViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
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
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Task,
                newItem: Task
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
