package br.com.eduardomaxwell.todolist.data.converter

import androidx.room.TypeConverter
import br.com.eduardomaxwell.todolist.data.model.Priority

class Converter {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}