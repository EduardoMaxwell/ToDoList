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
import br.com.eduardomaxwell.todolist.model.Task

class TaskAdapter : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DIFF_CALLBACK) {

    var listenerEdit: (Task) -> Unit = {}
    var listenerDelete: (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        private val title = binding.tvTitle
        private val dateTime = binding.tvDateTime
        private val description = binding.tvDescription
        private val more = binding.ivMore

        @SuppressLint("SetTextI18n")
        fun bind(task: Task) {
            title.text = task.taskTitle
            description.text = task.taskDescription
            dateTime.text = "${task.taskDate} ${task.taskHour}"
            more.setOnClickListener {
                showPopUp(task)
            }
        }

        private fun showPopUp(task: Task) {
            val popupMenu = PopupMenu(more.context, more)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionEdit -> listenerEdit(task)
                    R.id.actionDelete -> listenerDelete(task)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(
                oldItem: Task,
                newItem: Task
            ): Boolean {
                return oldItem.taskTitle == newItem.taskTitle
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
