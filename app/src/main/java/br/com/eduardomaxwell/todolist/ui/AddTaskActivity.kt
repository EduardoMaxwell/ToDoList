package br.com.eduardomaxwell.todolist.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import br.com.eduardomaxwell.todolist.databinding.ActivityAddTaskBinding
import br.com.eduardomaxwell.todolist.extensions.dateFormat
import br.com.eduardomaxwell.todolist.extensions.text
import br.com.eduardomaxwell.todolist.model.Task
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    var calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
        intent.getStringExtra("idTask")
    }

    private fun setListeners() {

        binding.edtDate.editText?.setOnClickListener {
            setDateEdit()
        }

        binding.edtHour.editText?.setOnClickListener {
            setHourEdit()
        }

        binding.btnCreateTask.setOnClickListener {
            createNewTask()
            finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun createNewTask() {
        val replyIntent = Intent()



        if (TextUtils.isEmpty(binding.edtTitle.text) && TextUtils.isEmpty(binding.edtDescription.text)) {

            setResult(RESULT_CANCELED, replyIntent)

        } else {

            val taskTitle = binding.edtTitle.text
            val taskDescription = binding.edtDescription.text
            val taskDate = binding.edtDate.text
            val taskHour = binding.edtHour.text

            val task = Task(
                taskTitle = taskTitle,
                taskDescription = taskDescription,
                taskDate = taskDate,
                taskHour = taskHour
            )

            val gson = Gson()
            val passableTask = gson.toJson(task)

            replyIntent.putExtra(EXTRA_TASK, passableTask)
            setResult(RESULT_OK, replyIntent)
        }
    }

    private fun setHourEdit() {
        val timeSetListener =
            TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)

                binding.edtHour.text = SimpleDateFormat("HH:mm").format(calendar.time)
            }
        TimePickerDialog(
            this@AddTaskActivity,
            timeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun setDateEdit() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                binding.edtDate.text = calendar.time.dateFormat()
            }

        DatePickerDialog(
            this@AddTaskActivity,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        ).show()
    }

    companion object {
        const val EXTRA_TASK = "task_extra"
        const val CREATE_NEW_TASK = 1000
        const val TASK_ID = "task_id"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, AddTaskActivity::class.java)
        }
    }
}