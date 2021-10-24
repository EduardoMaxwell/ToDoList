package br.com.eduardomaxwell.todolist.ui.viewmodel

import androidx.lifecycle.*
import br.com.eduardomaxwell.todolist.model.Task
import br.com.eduardomaxwell.todolist.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val allTasks: LiveData<List<Task>> = repository.allTask.asLiveData()

    fun insert(task: Task) = viewModelScope.launch {
        repository.insertTask(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
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