package com.example.todoapp.ui.category.detailedCategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.TaskAdapter
import com.example.todoapp.databinding.FragmentCategoryWithTaskListBinding
import com.example.todoapp.model.Category

class CategoryWithTaskListFragment : Fragment() {
    private var _binding: FragmentCategoryWithTaskListBinding? = null
    private val binding get() = _binding!!

    private val args: CategoryWithTaskListFragmentArgs by navArgs()
    private val viewModel: CategoryWithTaskListViewModel by viewModels() {
        CategoryWithTaskListViewModel.CategoryWithTaskListViewModelFactory(requireActivity().application)
    }
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryWithTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        observeCategory(args.categoryId)
        observeTasks(args.categoryId)
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter { task ->
            val action =
                CategoryWithTaskListFragmentDirections.actionCategoryWithTaskListFragmentToDetailedTaskFragment(
                    task.id,
                    task.categoryId
                )
            findNavController().navigate(action)
        }

        binding.rvCategoryTaskList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter
        }
    }

    private fun observeCategory(categoryId: Long) {
        viewModel.getCategoryById(categoryId).observe(viewLifecycleOwner) { category ->
            category?.let {
                binding.tvCategoryName.text = it.title
            }
        }
    }

    private fun observeTasks(categoryId: Long) {
        viewModel.getTasksForCategory(categoryId).observe(viewLifecycleOwner) { tasks ->
            if (tasks.isEmpty()) {
                binding.tvNoTasks.visibility = View.VISIBLE
                binding.rvCategoryTaskList.visibility = View.GONE
            } else {
                binding.tvNoTasks.visibility = View.GONE
                binding.rvCategoryTaskList.visibility = View.VISIBLE
                taskAdapter.submitList(tasks)
            }
        }

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categories.forEach { category ->
                taskAdapter.updateTaskCategory(category.id, category.title)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
