package com.example.todoapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.adapter.CategoryAdapter
import com.example.todoapp.adapter.TaskGroupAdapter
import com.example.todoapp.databinding.FragmentHomeScreenBinding
import com.example.todoapp.model.Category
import com.example.todoapp.viewmodel.CategoryViewModel

class HomeScreenFragment : Fragment() {

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var taskGroupAdapter: TaskGroupAdapter
    private val categoryViewModel: CategoryViewModel by viewModels()

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

        // Setup RecyclerView for categories (horizontal)
        categoryAdapter = CategoryAdapter(emptyList())
        binding.categoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        // Setup RecyclerView for task groups (vertical)
        taskGroupAdapter = TaskGroupAdapter(emptyList())
        binding.taskGroupRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskGroupAdapter
        }

        // Load dummy data and update adapters
        loadDummyCategories()
        loadDummyTaskGroups()
    }

    private fun loadDummyCategories() {
        val dummyCategories = listOf(
            Category(1, "Work", 50),
            Category(2, "Study", 70),
            Category(3, "Entertainment", 20),
            Category(4, "Shopping", 90)
        )
        categoryAdapter = CategoryAdapter(dummyCategories)
        binding.categoryRecyclerView.adapter = categoryAdapter
    }

    private fun loadDummyTaskGroups() {
        val dummyTaskGroups = listOf(
            Category(1, "Work", 50),
            Category(2, "Study", 70),
            Category(3, "Entertainment", 20),
            Category(4, "Shopping", 90)
        )
        taskGroupAdapter = TaskGroupAdapter(dummyTaskGroups)
        binding.taskGroupRecyclerView.adapter = taskGroupAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}