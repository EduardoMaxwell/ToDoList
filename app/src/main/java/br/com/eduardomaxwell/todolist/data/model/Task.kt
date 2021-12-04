package br.com.eduardomaxwell.todolist.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tb_task")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val taskTitle: String,
    @ColumnInfo(name = "description")
    val taskDescription: String,
    @ColumnInfo(name = "date")
    val taskDate: String? = null,
    @ColumnInfo(name = "hour")
    val taskHour: String? = null,
    @ColumnInfo(name = "priority")
    val priority: Priority
) : Parcelable {


    override fun hashCode(): Int {
        var result = id
        result = 31 * result + taskTitle.hashCode()
        result = 31 * result + taskDescription.hashCode()
        result = 31 * result + (taskDate?.hashCode() ?: 0)
        result = 31 * result + (taskHour?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false
        if (taskTitle != other.taskTitle) return false
        if (taskDescription != other.taskDescription) return false
        if (taskDate != other.taskDate) return false
        if (taskHour != other.taskHour) return false

        return true
    }
}