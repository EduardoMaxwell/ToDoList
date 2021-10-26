package br.com.eduardomaxwell.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.eduardomaxwell.todolist.extensions.dateFormat
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "tb_task")
class Task (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val taskTitle: String,
    @ColumnInfo(name = "description")
    val taskDescription: String? = null,
    @ColumnInfo(name = "date")
    val taskDate: String? = Calendar.getInstance().time.dateFormat(),
    @ColumnInfo(name = "hour")
    val taskHour: String? = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
)
