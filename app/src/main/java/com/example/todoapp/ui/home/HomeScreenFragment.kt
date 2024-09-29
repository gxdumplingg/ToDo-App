package com.example.todoapp.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.CategoryAdapter
import com.example.todoapp.adapter.TaskAdapter
import com.example.todoapp.databinding.FragmentHomeScreenBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.task.viewTask.ViewTaskFragmentDirections
import com.example.todoapp.viewmodel.CategoryViewModel

class HomeScreenFragment : Fragment() {

    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryViewModel: CategoryViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter
    private val viewModel: HomeScreenViewModel by viewModels {
        HomeScreenViewModel.HomeScreenViewModelFactory(requireActivity().application)
    }

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchBar()
        setupCategoryRecyclerView()
        setupAddTaskButton()
        setupViewTaskButton()

        setupInProgressRecyclerView()
        observeInProgressTasks()
    }

    private fun setupSearchBar() {
        val searchText = binding.searchBar.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchText.hint = getString(R.string.search_task)
        searchText.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        searchText.setTextColor(Color.BLACK)
    }
    private fun setupInProgressRecyclerView() {
        taskAdapter = TaskAdapter { task -> onTaskClick(task) }
        binding.recyclerviewInProgress.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
    private fun observeInProgressTasks() {
        viewModel.inProgressTasks.observe(viewLifecycleOwner) { tasks ->
            taskAdapter.submitList(tasks)
        }
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categories.forEach { category ->
                taskAdapter.updateTaskCategory(category.id, category.title)
            }
        }
    }

    private fun onTaskClick(task: Task) {
        val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToDetailedTaskFragment(
            task.id,
            task.categoryId)
        findNavController().navigate(action)
    }
    private fun setupCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter(emptyList())
        binding.recyclerviewTaskGroup.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        categoryViewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            categories?.let {
                categoryAdapter = CategoryAdapter(it)
                binding.recyclerviewTaskGroup.adapter = categoryAdapter
            }
        }
    }
    private fun setupViewTaskButton(){
        binding.btnViewTasks.setOnClickListener{
            val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToViewTaskFragment()
            findNavController().navigate(action)
        }
    }
    private fun setupAddTaskButton() {
        binding.btnAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreenFragment_to_addTaskFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}