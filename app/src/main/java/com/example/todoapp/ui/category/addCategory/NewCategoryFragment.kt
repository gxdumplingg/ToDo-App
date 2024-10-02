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
    private var selectedView: View? = null

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
        binding.colorOptionYellow.setOnClickListener { selectColor(binding.colorOptionYellow, R.color.bright_yellow) }
        binding.colorOptionRed.setOnClickListener { selectColor(binding.colorOptionRed, R.color.red) }
        binding.colorOptionGreen.setOnClickListener { selectColor(binding.colorOptionGreen, R.color.green) }
        binding.colorOptionLightGray.setOnClickListener { selectColor(binding.colorOptionLightGray, R.color.light_gray) }
        binding.colorOptionDarkGray.setOnClickListener { selectColor(binding.colorOptionDarkGray, R.color.dark_gray) }
        binding.colorOptionDarkBlue.setOnClickListener { selectColor(binding.colorOptionDarkBlue, R.color.dark_blue) }
    }

    private fun selectColor(view: View, colorRes: Int) {
        // Reset previously selected view if there was one
        selectedView?.background = ContextCompat.getDrawable(requireContext(), R.drawable.circle_background)

        // Set the new selected view
        selectedView = view
        selectedColor = ContextCompat.getColor(requireContext(), colorRes)

        // Update the background to show selection
        selectedView?.background = ContextCompat.getDrawable(requireContext(), R.drawable.circle_with_check)
    }

    private fun setupAddCategoryListener() {
        binding.btnAddCategory.setOnClickListener {
            val categoryTitle = binding.etSCategoryTitle.text.toString().trim()

            if (categoryTitle.isNotBlank() && selectedColor != 0) {
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
