package br.com.eduardomaxwell.todolist

import android.app.Application
import br.com.eduardomaxwell.todolist.data.datasource.TaskRoomDatabase
import br.com.eduardomaxwell.todolist.data.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TaskApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { TaskRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { TaskRepository(database.taskDao()) }
}