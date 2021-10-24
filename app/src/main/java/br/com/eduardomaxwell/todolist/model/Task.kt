package br.com.eduardomaxwell.todolist.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tb_task")

class Task(
    @PrimaryKey @ColumnInfo(name = "title") val title: String,
)
