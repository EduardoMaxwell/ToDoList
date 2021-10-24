package br.com.eduardomaxwell.todolist.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import br.com.eduardomaxwell.todolist.databinding.ActivityAddTaskBinding
import br.com.eduardomaxwell.todolist.extensions.dateFormat
import br.com.eduardomaxwell.todolist.extensions.text
import br.com.eduardomaxwell.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
    }

    private fun setListeners() {
        binding.edtDate.editText?.setOnClickListener {

            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener { date ->
                val timeZone = TimeZone.getDefault()
                val offSet = timeZone.getOffset(Date().time) * -1

                binding.edtDate.text = Date(date + offSet).dateFormat()
            }

            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.edtHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                val minute =
                    if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute

                binding.edtHour.text = "$hour:$minute"
            }

            timePicker.show(supportFragmentManager, "TIME_PICKER_TAG")
        }

        binding.btnCreateTask.setOnClickListener {

            val replyIntent = Intent()
            if (TextUtils.isEmpty(binding.edtTitle.text) && TextUtils.isEmpty(binding.edtDescription.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)

            } else {

                val task = binding.edtTitle.text.toString()

                replyIntent.putExtra(EXTRA_REPLY, task)
                replyIntent.putExtra(EXTRA_REPLY, task)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }


    companion object {
        const val EXTRA_REPLY = "br.com.eduardomaxwell.todolist.model.Task"
        const val CREATE_NEW_TASK = 1000
        const val TASK_ID = "task_id"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, AddTaskActivity::class.java)
        }
    }
}