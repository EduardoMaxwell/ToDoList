package br.com.eduardomaxwell.todolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import br.com.eduardomaxwell.todolist.R
import br.com.eduardomaxwell.todolist.TaskApplication
import br.com.eduardomaxwell.todolist.databinding.FragmentTaskListBinding
import br.com.eduardomaxwell.todolist.ui.viewmodel.TaskViewModel
import br.com.eduardomaxwell.todolist.ui.viewmodel.TaskViewModelFactory

class TaskListFragment : Fragment() {

    private val viewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            (activity?.application as TaskApplication).repository
        )
    }

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TaskAdapter {
            val action =
                TaskListFragmentDirections.actionTaskListFragmentToTaskDetailFragment(it.id)
            this.findNavController().navigate(action)
        }

        setUpRecyclerView(adapter)
        setListeners()

    }

    private fun setListeners() {
        binding.fabAddTask.setOnClickListener {
            val action =
                TaskListFragmentDirections.actionTaskListFragmentToAddTaskFragment(
                    getString(R.string.add_fragment_title)
                )
            this.findNavController().navigate(action)
        }
        hideFloatingActionButton()
    }

    private fun hideFloatingActionButton() {
        binding.rvTasks.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && binding.fabAddTask.visibility == View.VISIBLE) {
                    binding.fabAddTask.hide()
                } else if (dy < 0 && binding.fabAddTask.visibility != View.VISIBLE) {
                    binding.fabAddTask.show()
                }
            }
        })
    }

    private fun setUpRecyclerView(adapter: TaskAdapter) {
        binding.rvTasks.adapter = adapter
        binding.rvTasks.addItemDecoration(
            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )

        viewModel.allTasks.observe(this.viewLifecycleOwner) { tasks ->
            tasks.let {
                if (tasks.isNotEmpty()) {
                    binding.empty.emptyState.visibility = View.GONE
                    binding.rvTasks.visibility = View.VISIBLE
                } else {
                    binding.empty.emptyState.visibility = View.VISIBLE
                    binding.rvTasks.visibility = View.GONE
                }
                adapter.submitList(it)
            }
        }
    }

}