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

        val searchText = binding.searchBar.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchText.hint = getString(R.string.search_task)
        searchText.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        searchText.setTextColor(Color.BLACK)

        // button add
        binding.btnAddTask.setOnClickListener{
            findNavController().navigate(R.id.action_homeScreenFragment_to_addTaskFragment)
        }
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
            Category(1, "Work", 50.4F),
            Category(2, "Study", 70.1F),
            Category(3, "Entertainment", 20.6F),
            Category(4, "Shopping", 15.0F)
        )
        categoryAdapter = CategoryAdapter(dummyCategories)
        binding.categoryRecyclerView.adapter = categoryAdapter
    }

    private fun loadDummyTaskGroups() {
        val dummyTaskGroups = listOf(
            Category(1, "Work", 50.0F),
            Category(2, "Study", 70.0F),
            Category(3, "Entertainment", 20.0F),
            Category(4, "Shopping", 80.5F)
        )
        taskGroupAdapter = TaskGroupAdapter(dummyTaskGroups)
        binding.taskGroupRecyclerView.adapter = taskGroupAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}