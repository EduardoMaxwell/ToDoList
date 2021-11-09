package br.com.eduardomaxwell.todolist.ui.viewmodel

import androidx.lifecycle.*
import br.com.eduardomaxwell.todolist.data.model.Task
import br.com.eduardomaxwell.todolist.data.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val allTasks: LiveData<List<Task>> = repository.allTask.asLiveData()

    fun isEntryValid(
        taskTitle: String,
        taskDescription: String,
    ): Boolean {
        if (taskTitle.isBlank() || taskDescription.isBlank()) {
            return false
        }
        return true
    }

    fun addNewTask(
        taskTitle: String,
        taskDescription: String,
        taskDate: String,
        taskHour: String
    ) {
        val newTask = getNewTask(taskTitle, taskDescription, taskDate, taskHour)
        insert(newTask)
    }

    fun updateTask(
        taskId: Int,
        taskTitle: String,
        taskDescription: String,
        taskDate: String,
        taskHour: String
    ) {
        val updatedTask = getUpdatedTask(taskId, taskTitle, taskDescription, taskDate, taskHour)
        update(updatedTask)
    }

    fun deleteTask(task: Task) {
        delete(task)
    }


    fun retrieveTask(id: Int): LiveData<Task> {
        return getTaskFromDb(id)
    }

    private fun insert(task: Task) = viewModelScope.launch {
        repository.insertTask(task)
    }

    private fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }

    private fun update(task: Task) = viewModelScope.launch {
        repository.update(task)
    }

    private fun getTaskFromDb(id: Int): LiveData<Task> {
        return repository.getTask(id)
    }

    private fun getNewTask(
        taskTitle: String,
        taskDescription: String,
        taskDate: String,
        taskHour: String
    ): Task {
        return Task(
            taskTitle = taskTitle,
            taskDescription = taskDescription,
            taskDate = taskDate,
            taskHour = taskHour
        )
    }

    private fun getUpdatedTask(
        taskId: Int,
        taskTitle: String,
        taskDescription: String,
        taskDate: String,
        taskHour: String
    ): Task {
        return Task(
            id = taskId,
            taskTitle = taskTitle,
            taskDescription = taskDescription,
            taskDate = taskDate,
            taskHour = taskHour
        )
    }

}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}