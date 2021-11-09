package br.com.eduardomaxwell.todolist.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tb_task")
@Parcelize
class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val taskTitle: String,
    @ColumnInfo(name = "description")
    val taskDescription: String,
    @ColumnInfo(name = "date")
    val taskDate: String? = null,
    @ColumnInfo(name = "hour")
    val taskHour: String? = null
) : Parcelable