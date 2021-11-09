package br.com.eduardomaxwell.todolist.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.eduardomaxwell.todolist.R
import br.com.eduardomaxwell.todolist.TaskApplication
import br.com.eduardomaxwell.todolist.data.model.Task
import br.com.eduardomaxwell.todolist.databinding.FragmentAddTaskBinding
import br.com.eduardomaxwell.todolist.extensions.dateFormat
import br.com.eduardomaxwell.todolist.extensions.text
import br.com.eduardomaxwell.todolist.ui.viewmodel.TaskViewModel
import br.com.eduardomaxwell.todolist.ui.viewmodel.TaskViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class AddTaskFragment : Fragment() {
    private val viewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory((activity?.application as TaskApplication).repository)
    }

    private var calendar: Calendar = Calendar.getInstance()

    private val navigationArgs: TaskDetailFragmentArgs by navArgs()

    lateinit var task: Task

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.taskId
        if (id > 0) {
            binding.btnCreateTask.text = getString(R.string.btn_txt_update_task)
            viewModel.retrieveTask(id).observe(this.viewLifecycleOwner) { taskSelected ->
                task = taskSelected
                bind(task)
            }
        } else {
            binding.btnCreateTask.setOnClickListener {
                addNewTask()
            }
        }
        setListerners()

    }

    private fun setListerners() {

        binding.edtHour.editText?.setOnClickListener {
            setHourEdit()
        }

        binding.edtDate.editText?.setOnClickListener {
            setDateEdit()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setHourEdit() {
        val timeSetListener =
            TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)

                binding.edtHour.text = SimpleDateFormat("HH:mm").format(calendar.time)
            }
        TimePickerDialog(
            requireContext(),
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
            requireContext(),
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
        ).show()
    }

    private fun addNewTask() {
        if (isEntryValid()) {
            viewModel.addNewTask(
                binding.edtTitle.text,
                binding.edtDescription.text,
                binding.edtDate.text,
                binding.edtHour.text
            )
        }
        val action = AddTaskFragmentDirections.actionAddTaskFragmentToTaskListFragment()
        findNavController().navigate(action)
    }

    private fun bind(task: Task) {
        binding.apply {
            edtTitle.text = task.taskTitle
            edtDescription.text = task.taskDescription
            edtDate.text = task.taskDate.toString()
            edtHour.text = task.taskHour.toString()
            btnCreateTask.setOnClickListener { updateTask() }

        }
    }

    private fun updateTask() {
        if (isEntryValid()) {
            viewModel.updateTask(
                this.navigationArgs.taskId,
                this.binding.edtTitle.text,
                this.binding.edtDescription.text,
                this.binding.edtDate.text,
                this.binding.edtHour.text
            )
            val action = AddTaskFragmentDirections.actionAddTaskFragmentToTaskListFragment()
            this.findNavController().navigate(action)
        }

    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.edtTitle.text,
            binding.edtDescription.text,
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

}