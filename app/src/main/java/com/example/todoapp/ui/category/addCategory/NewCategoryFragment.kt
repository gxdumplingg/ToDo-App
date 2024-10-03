package com.example.todoapp.ui.category.addCategory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
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
        binding.btnBack.setOnClickListener { showBackDialog() }
    }

    private fun setupColorOptions() {
        binding.apply {
            colorOptionYellow.setOnClickListener { selectColor(binding.colorOptionYellow, R.color.bright_yellow) }
            colorOptionRed.setOnClickListener { selectColor(binding.colorOptionRed, R.color.red) }
            colorOptionGreen.setOnClickListener { selectColor(binding.colorOptionGreen, R.color.green) }
            colorOptionLightGray.setOnClickListener { selectColor(binding.colorOptionLightGray, R.color.light_gray) }
            colorOptionDarkGray.setOnClickListener { selectColor(binding.colorOptionDarkGray, R.color.dark_gray) }
            colorOptionDarkBlue.setOnClickListener { selectColor(binding.colorOptionDarkBlue, R.color.dark_blue) }
        }
    }

    private fun selectColor(view: View, colorRes: Int) {
        selectedView?.background = ContextCompat.getDrawable(requireContext(), R.drawable.circle_background)
        selectedView = view
        selectedColor = ContextCompat.getColor(requireContext(), colorRes)
        selectedView?.background = ContextCompat.getDrawable(requireContext(), R.drawable.circle_with_check)
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
