package com.example.todoapp.ui.category.detailedCategory

import  android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.ColorAdapter
import com.example.todoapp.databinding.FragmentDetailedCategoryBinding
import com.example.todoapp.model.Category
import com.example.todoapp.ui.dialog.CustomDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailedCategoryFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailedCategoryBinding? = null
    private val binding get() = _binding!!
    private val args: DetailedCategoryFragmentArgs by navArgs()
    private val viewModel: DetailedCategoryViewModel by viewModels {
        DetailedCategoryViewModel.DetailedCategoryViewModelFactory(requireActivity().application)
    }
    private var selectedColor: Int = 0
    private var selectedCategoryId: Long? = null
    private var categories: List<Category> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.categories.observe(viewLifecycleOwner) { categoriesList ->
            categories = categoriesList
            val category = categories.find { it.id == args.categoryId }
            binding.tvCategoryTitle.text = category?.title ?: "Unknown Category"
        }
        viewModel.getCategoryById(args.categoryId).observe(viewLifecycleOwner) { category ->
            category?.let {
                bindCategoryData(it)
            }
        }

        binding.btnBack.setOnClickListener { showBackDialog() }

        binding.icDelete.setOnClickListener {
            viewModel.getCategoryById(args.categoryId).observe(viewLifecycleOwner) { category ->
                category?.let {
                    showDeleteConfirmationDialog(it)
                }
            }
        }
//        binding.tvCategoryTitle.setOnClickListener { showCategoryDropdownMenu() }
        binding.btnSaveNewCategory.setOnClickListener { showSaveDialog() }
        binding.icAddNewCategory.setOnClickListener{ findNavController().navigate(R.id.action_detailedCategoryFragment_to_newCategoryFragment)}
        setupColorOptions()
    }

    private fun bindCategoryData(category: Category) {
        binding.tvCategoryTitle.text = category.title
        selectedColor = category.color
        updateSelectedColorView(selectedColor)
    }

    private fun updateSelectedColorView(color: Int) {
        selectedColor = color

    }

    private fun saveUpdatedCategory() {
        viewModel.getCategoryById(args.categoryId).observe(viewLifecycleOwner) { category ->
            category?.let {
                val updatedCategory = it.copy(
                    title = binding.tvCategoryTitle.text.toString(),
                    color = selectedColor
                )
                viewModel.updateCategory(updatedCategory)
                findNavController().popBackStack()
            }
        }
    }
    @SuppressLint("InflateParams")
    private fun showCategoryDropdownMenu() {
        val popupView = layoutInflater.inflate(R.layout.menu_category_dropdown, null)
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        val categoryContainer = popupView as LinearLayout
        categoryContainer.removeAllViews()

        categories.forEach { category ->
            val categoryView = LayoutInflater.from(requireContext())
                .inflate(R.layout.menu_category_dropdown, categoryContainer, false)
            val categoryTitle = categoryView.findViewById<TextView>(R.id.category_title)
            categoryTitle.text = category.title
            categoryView.setOnClickListener {
                updateCategory(category.title, category.id, popupWindow)
            }
            categoryContainer.addView(categoryView)
        }

        popupWindow.showAsDropDown(binding.tvCategoryTitle)
    }
    private fun updateCategory(categoryName: String, categoryId: Long, popupWindow: PopupWindow) {
        binding.tvCategoryTitle.text = categoryName
        selectedCategoryId = categoryId
        popupWindow.dismiss()
    }

    private fun showDeleteConfirmationDialog(category: Category) {
        val dialogView = layoutInflater.inflate(R.layout.delete_dialog, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        btnConfirm.setOnClickListener {
            viewModel.deleteCategory(category)
            dialog.dismiss()
            findNavController().popBackStack()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showBackDialog() {
        val customDialog = CustomDialog(requireContext()).apply {
            message = "Changes you made may not be saved. Do you want to leave?"
            onConfirmClickListener = {
                findNavController().popBackStack()
            }
        }
        customDialog.show()
    }

    private fun showSaveDialog() {
        val customDialog = CustomDialog(requireContext()).apply {
            message = "Are you sure you want to update new category?"
            onConfirmClickListener = {
                saveUpdatedCategory()
            }
        }
        customDialog.show()
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
            updateSelectedColorView(selectedColor)
        }
        binding.rvColorOptions.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvColorOptions.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}