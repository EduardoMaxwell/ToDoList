package br.com.eduardomaxwell.todolist.datasource

import androidx.room.*
import br.com.eduardomaxwell.todolist.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tb_task ORDER BY date ASC")
    fun getAll(): Flow<List<Task>>

    @Query("SELECT * FROM tb_task WHERE id = :id")
    fun getTask(id: Int): Flow<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM tb_task")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)
}
