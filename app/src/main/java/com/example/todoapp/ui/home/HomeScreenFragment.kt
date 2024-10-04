package com.example.todoapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.CategoryAdapter
import com.example.todoapp.adapter.TaskAdapter
import com.example.todoapp.databinding.FragmentHomeScreenBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.category.allCategories.CategoryViewModel

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
        setupCategoryRecyclerView()
        setupAddTaskButton()
        setupViewTaskButton()

        setupInProgressRecyclerView()
        observeInProgressTasks()

        viewModel.completionPercentage.observe(viewLifecycleOwner) { percentage ->
            updateProgressCircle(percentage)
            binding.progressCircle.progress = percentage
            binding.tvPercentage.text = "$percentage%"
        }

        binding.tvViewAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeScreenFragment_to_viewAllCategoriesFragment)
        }
    }

    private fun updateProgressCircle(percentage: Int) {
        val progressBar = view?.findViewById<ProgressBar>(R.id.progressCircle)
        progressBar?.progress = percentage
    }

    private fun setupInProgressRecyclerView() {
        taskAdapter = TaskAdapter { task -> onTaskClick(task) }
        binding.recyclerviewInProgress.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeInProgressTasks() {
        viewModel.inProgressTasks.observe(viewLifecycleOwner) { tasks ->
            binding.tvInProgressNumber.text = "${tasks.size}"
            if (tasks.isEmpty()) {
                binding.tvNoTasks.visibility = View.VISIBLE
                binding.recyclerviewInProgress.visibility = View.GONE
            } else {
                binding.tvNoTasks.visibility = View.GONE
                binding.recyclerviewInProgress.visibility = View.VISIBLE
                taskAdapter.submitList(tasks)
            }
        }
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categories.forEach { category ->
                taskAdapter.updateTaskCategory(category.id, category.title)
                binding.tvCategoryNumber.text = "${categories.size}"
            }
        }
    }

    private fun onTaskClick(task: Task) {
        val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToDetailedTaskFragment(
            task.id,
            task.categoryId
        )
        findNavController().navigate(action)
    }

    private fun setupCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter(emptyList(), emptyList(),
            onCategoryClick = { categoryId ->
                val action = HomeScreenFragmentDirections
                    .actionHomeScreenFragmentToCategoryWithTaskListFragment(categoryId)
                findNavController().navigate(action)
            },
            onMoreClick = { categoryId ->
                val action =
                    HomeScreenFragmentDirections.actionHomeScreenFragmentToDetailedCategoryFragment(
                        categoryId
                    )
                findNavController().navigate(action)
            }
        )
        binding.recyclerviewTaskGroup.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        categoryViewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            viewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
                categoryAdapter.updateData(categories, tasks)
            }
        }
    }

    private fun setupViewTaskButton() {
        binding.btnViewTasks.setOnClickListener {
            val action =
                HomeScreenFragmentDirections.actionHomeScreenFragmentToViewTaskFragment()
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
