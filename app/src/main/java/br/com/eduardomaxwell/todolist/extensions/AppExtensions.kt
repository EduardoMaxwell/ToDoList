package br.com.eduardomaxwell.todolist.extensions

import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale("pt", "BR")

fun Date.dateFormat(): String {
    return SimpleDateFormat("dd/MM/yyyy", locale).format(this)
}

fun Calendar.dateFormat(): String{
    return SimpleDateFormat("dd/MM/yyyy", locale).format(this)
}
var TextInputLayout.text: String
    get() = editText?.text.toString()
    set(value) {
        editText?.setText(value)
    }