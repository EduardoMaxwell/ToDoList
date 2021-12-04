package br.com.eduardomaxwell.todolist.ui.viewmodel

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.com.eduardomaxwell.todolist.R
import br.com.eduardomaxwell.todolist.data.model.Priority
import br.com.eduardomaxwell.todolist.data.model.Task


class SharedViewModel(application: Application) : AndroidViewModel(application){

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)

    fun verifyEmptyList(list: List<Task>){
        emptyDatabase.value = list.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position){
                0 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.high_priority))}
                1 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.medium_priority))}
                2 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.low_priority))}
            }
        }
    }

    fun parseIntToPriority(priority: Int): Priority {

        return when(priority){
            0 -> {Priority.HIGH}
            1 -> {Priority.MEDIUM}
            2 -> {Priority.LOW}
            else -> Priority.LOW
        }
    }

    fun parsePriorityToInt(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }

    fun verifyDataFromUser(title: String, description: String): Boolean{
        return !(title.isEmpty() || description.isEmpty())
    }
}