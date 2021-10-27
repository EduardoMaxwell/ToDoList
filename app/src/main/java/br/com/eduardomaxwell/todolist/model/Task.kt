package br.com.eduardomaxwell.todolist.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.eduardomaxwell.todolist.extensions.dateFormat
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "tb_task")
@Parcelize
class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val taskTitle: String,
    @ColumnInfo(name = "description")
    val taskDescription: String? = null,
    @ColumnInfo(name = "date")
    val taskDate: String,
    @ColumnInfo(name = "hour")
    val taskHour: String
) : Parcelable
