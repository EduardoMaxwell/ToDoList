package br.com.eduardomaxwell.todolist.ui.detailtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.eduardomaxwell.todolist.R
import br.com.eduardomaxwell.todolist.TaskApplication
import br.com.eduardomaxwell.todolist.data.model.Task
import br.com.eduardomaxwell.todolist.databinding.FragmentTaskDetailBinding
import br.com.eduardomaxwell.todolist.ui.viewmodel.TaskViewModel
import br.com.eduardomaxwell.todolist.ui.viewmodel.TaskViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TaskDetailFragment : Fragment() {
    private val viewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            (activity?.application as TaskApplication).repository
        )
    }
    private val navigationArgs: TaskDetailFragmentArgs by navArgs()

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var task: Task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.taskId

        viewModel.retrieveTask(id).observe(this.viewLifecycleOwner) { taskSelected ->
            task = taskSelected
            bind(task)
        }
    }

    private fun bind(task: Task) {
        binding.apply {
            edtTitle.text = task.taskTitle
            edtDescription.text = task.taskDescription
            edtDate.text = task.taskDate
            edtHour.text = task.taskHour
            btnCancel.setOnClickListener { showConfirmationDialog() }
            fabEditTask.setOnClickListener {
                editTask()
            }
        }
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Attention")
            .setMessage("Are you sure you want to delete?")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes, I'm sure") { _, _ ->
                deleteTask()
            }.show()
    }

    private fun deleteTask() {
        viewModel.deleteTask(task)
        findNavController().navigateUp()
    }

    private fun editTask() {
        val action = TaskDetailFragmentDirections.actionTaskDetailFragmentToAddTaskFragment(
            getString(R.string.edit_fragment_title),
            task.id
        )
        this.findNavController().navigate(action)
    }
}