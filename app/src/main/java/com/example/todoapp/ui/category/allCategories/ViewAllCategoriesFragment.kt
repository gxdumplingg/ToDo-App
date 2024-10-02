package com.example.todoapp.ui.category.allCategories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.CategoryAdapter
import com.example.todoapp.databinding.FragmentViewAllCategoriesBinding
import com.example.todoapp.ui.home.HomeScreenViewModel

class ViewAllCategoriesFragment : Fragment() {

    private var _binding: FragmentViewAllCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter

    private val categoryViewModel: CategoryViewModel by viewModels() {
        CategoryViewModel.CategoryViewModelFactory(requireActivity().application)
    }
    private val taskViewModel: HomeScreenViewModel by viewModels() {
        HomeScreenViewModel.HomeScreenViewModelFactory(requireActivity().application)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewAllCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCategories.layoutManager = GridLayoutManager(context, 2)
        categoryAdapter = CategoryAdapter(emptyList(), emptyList())
        binding.rvCategories.adapter = categoryAdapter

        categoryViewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
                categoryAdapter = CategoryAdapter(categories, tasks)
                binding.rvCategories.adapter = categoryAdapter
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnAddCategory.setOnClickListener{
            findNavController().navigate(R.id.action_viewAllCategoriesFragment_to_newCategoryFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
