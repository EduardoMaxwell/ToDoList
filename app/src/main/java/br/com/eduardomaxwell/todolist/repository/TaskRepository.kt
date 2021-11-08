package br.com.eduardomaxwell.todolist.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import br.com.eduardomaxwell.todolist.datasource.TaskDao
import br.com.eduardomaxwell.todolist.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {

    val allTask: Flow<List<Task>> = dao.getAll()

    fun getTask(id: Int): LiveData<Task>{
        return dao.getTask(id).asLiveData()
    }

    @WorkerThread
    suspend fun insertTask(task: Task){
        dao.insert(task)
    }

    suspend fun delete(task: Task) {
       dao.delete(task)
    }

    suspend fun update(task: Task) {
        dao.update(task)
    }
}