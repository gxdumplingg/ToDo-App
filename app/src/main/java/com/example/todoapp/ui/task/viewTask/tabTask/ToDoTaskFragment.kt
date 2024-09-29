package com.example.todoapp.ui.task.viewTask.tabTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.adapter.TaskAdapter
import com.example.todoapp.databinding.FragmentToDoTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.task.viewTask.ViewTaskFragmentDirections
import com.example.todoapp.ui.task.viewTask.ViewTasksViewModel

class ToDoTaskFragment : Fragment() {
    private var _binding: FragmentToDoTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter
    private val viewModel: ViewTasksViewModel by viewModels {
        ViewTasksViewModel.ViewTasksViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDoTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeTasks()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter { task -> onTaskClick(task) }
        binding.recyclerViewToDoTask.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeTasks() {
        viewModel.todoTasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.submitList(tasks)
        }
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categories.forEach { category ->
                taskAdapter.updateTaskCategory(category.id, category.title)
            }
        }
    }

    private fun onTaskClick(task: Task) {
        val action = ViewTaskFragmentDirections.actionViewTaskFragmentToDetailedTaskFragment(
            task.id,
            task.categoryId
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}