package com.example.todoapp.ui.category.addCategory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.ColorAdapter
import com.example.todoapp.databinding.FragmentNewCategoryBinding
import com.example.todoapp.model.Category
import com.example.todoapp.ui.category.allCategories.CategoryViewModel
import com.example.todoapp.ui.dialog.CustomDialog

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
        binding.btnBack.setOnClickListener { showBackDialog() }
    }

    private fun setupColorOptions() {

        val colors = listOf(
            R.color.bright_yellow,
            R.color.red,
            R.color.green,
            R.color.light_gray,
            R.color.dark_gray,
            R.color.dark_blue,
            R.color.brown,
            R.color.dark_brown,
            R.color.olive_green,
        )

        val adapter = ColorAdapter(requireContext(), colors) { selectedColorResId ->
            val selectedColor = ContextCompat.getColor(requireContext(), selectedColorResId)
            this.selectedColor = selectedColor
        }

        binding.rvColorOptions.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvColorOptions.adapter = adapter
    }

    private fun setupAddCategoryListener() {
        binding.btnAddCategory.setOnClickListener {
            val categoryTitle = binding.etSCategoryTitle.text.toString().trim()

            Log.d("NewCategoryFragment", "Category Title: $categoryTitle")
            Log.d("NewCategoryFragment", "Selected Color: $selectedColor")

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
                if (categoryTitle.isBlank()) {
                    binding.etSCategoryTitle.error = "Please enter category title."
                }
                if (selectedColor == 0) {
                    Toast.makeText(requireContext(), "Please select a color.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun showBackDialog(){
        val customDialog = CustomDialog(requireContext()).apply {
            message = "Changes you made may not be saved. \nDo you want to leave?"
            onConfirmClickListener = {
                findNavController().popBackStack()
            }
        }
        customDialog.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
