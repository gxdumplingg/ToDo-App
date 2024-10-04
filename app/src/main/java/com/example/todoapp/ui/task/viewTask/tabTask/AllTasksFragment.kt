package com.example.todoapp.ui.task.viewTask.tabTask

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.TaskAdapter
import com.example.todoapp.databinding.FragmentAllTasksBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.task.viewTask.ViewTaskFragmentDirections
import com.example.todoapp.ui.task.viewTask.ViewTasksViewModel

class AllTasksFragment : Fragment() {

    private var _binding: FragmentAllTasksBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter
    private val viewModel: ViewTasksViewModel by viewModels {
        ViewTasksViewModel.ViewTasksViewModelFactory(requireActivity().application)
    }
    private var isGridLayout = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.icFilter.setOnClickListener { showSortMenu() }

        binding.btnGridLayout.setOnClickListener {
            toggleLayout()
        }

        setupRecyclerView()
        observeTasks()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter { task ->
            onTaskClick(task)
        }
        binding.recyclerViewAllTasks.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeTasks() {
        viewModel.activeTasks.observe(viewLifecycleOwner) { tasks ->
            binding.tvAllTasksNumber.text = "Total: ${tasks.size}"
            if (tasks.isEmpty()) {
                binding.tvNoTasks.visibility = View.VISIBLE
                binding.recyclerViewAllTasks.visibility = View.GONE
            } else {
                binding.tvNoTasks.visibility = View.GONE
                binding.recyclerViewAllTasks.visibility = View.VISIBLE
                taskAdapter.submitList(tasks)
            }
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

    private fun showSortMenu() {
        val popupView = layoutInflater.inflate(R.layout.menu_popup_filter, null)

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupView.findViewById<TextView>(R.id.sort_due_date_ascending).setOnClickListener {
            viewModel.sortAllTasksByDueDateAscending()
            popupWindow.dismiss()
        }

        popupView.findViewById<TextView>(R.id.sort_due_date_descending).setOnClickListener {
            viewModel.sortAllTasksByDueDateDescending()
            popupWindow.dismiss()
        }

        popupWindow.showAsDropDown(binding.icFilter, 0, 0)
    }

    private fun toggleLayout() {
        isGridLayout = !isGridLayout
        if (isGridLayout) {
            binding.recyclerViewAllTasks.layoutManager = GridLayoutManager(context, 2)
            taskAdapter.setIsGridLayout(true)
        } else {
            binding.recyclerViewAllTasks.layoutManager = LinearLayoutManager(context)
            taskAdapter.setIsGridLayout(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
