package com.example.todoapp.ui.category.addCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentNewCategoryBinding
import com.example.todoapp.model.Category
import com.example.todoapp.ui.category.allCategories.CategoryViewModel

class NewCategoryFragment : Fragment() {

    private var _binding: FragmentNewCategoryBinding? = null
    private val binding get() = _binding!!
    private val categoryViewModel: CategoryViewModel by viewModels() {
        CategoryViewModel.CategoryViewModelFactory(requireActivity().application)
    }

    private var selectedColor: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupColorOptions()
        setupAddCategoryListener()
    }

    private fun setupColorOptions() {
        val yellowColor = ContextCompat.getColor(requireContext(), R.color.bright_yellow)
        val redColor = ContextCompat.getColor(requireContext(), R.color.red)
        val greenColor = ContextCompat.getColor(requireContext(), R.color.green)
        val lightGrayColor = ContextCompat.getColor(requireContext(), R.color.light_gray)

        binding.colorOptionYellow.setOnClickListener {
            selectedColor = yellowColor
            updateSelectedColor(yellowColor)
        }

        binding.colorOptionRed.setOnClickListener {
            selectedColor = redColor
            updateSelectedColor(redColor)
        }

        binding.colorOptionGreen.setOnClickListener {
            selectedColor = greenColor
            updateSelectedColor(greenColor)
        }

        binding.colorOptionLightGray.setOnClickListener {
            selectedColor = lightGrayColor
            updateSelectedColor(lightGrayColor)
        }
    }

    private fun updateSelectedColor(color: Int) {
        selectedColor = color
    }

    private fun setupAddCategoryListener() {
        binding.btnAddCategory.setOnClickListener {
            val categoryTitle = binding.etSCategoryTitle.text.toString().trim()

            if (categoryTitle.isNotBlank()) {
                val newCategory = Category(
                    id = 0,
                    title = categoryTitle,
                    completedPercent = 0f,
                    color = selectedColor
                )

                categoryViewModel.addCategory(newCategory)
                findNavController().popBackStack()
            } else {
                binding.etSCategoryTitle.error = "Please enter category title."
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

