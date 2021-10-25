package br.com.eduardomaxwell.todolist.ui

import android.annotation.SuppressLint
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
    }

    @SuppressLint("SimpleDateFormat")
    private fun setListeners() {


        binding.edtDate.editText?.setOnClickListener {
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

/*            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener { date ->
                val timeZone = TimeZone.getDefault()
                val offSet = timeZone.getOffset(Date().time) * -1

                binding.edtDate.text = Date(date + offSet).dateFormat()
            }

            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")*/
        }

        binding.edtHour.editText?.setOnClickListener {
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

/*            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                val minute =
                    if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute

                binding.edtHour.text = "$hour:$minute"
            }

            timePicker.show(supportFragmentManager, "TIME_PICKER_TAG")*/
        }

        binding.btnCreateTask.setOnClickListener {

            val replyIntent = Intent()
            if (TextUtils.isEmpty(binding.edtTitle.text) && TextUtils.isEmpty(binding.edtDescription.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)

            } else {

                val task = binding.edtTitle.text

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