package com.example.todoapp.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.PopupMenu
import android.widget.PopupWindow
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddTaskBinding


class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.icCategoryDropDown.setOnClickListener {
            showCategoryDropdownMenu()
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showCategoryDropdownMenu() {
        val popupView = layoutInflater.inflate(R.layout.custom_category_dropdown, null)
        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        // Setup click listeners for menu items
        popupView.findViewById<TextView>(R.id.menu_item_work).setOnClickListener {
            binding.tvSelectedCategory.text = "Work"
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.menu_item_education).setOnClickListener {
            binding.tvSelectedCategory.text = "Education"
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.menu_item_entertainment).setOnClickListener {
            binding.tvSelectedCategory.text = "Entertainment"
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.menu_item_personal).setOnClickListener {
            binding.tvSelectedCategory.text = "Personal"
            popupWindow.dismiss()
        }

        popupWindow.showAsDropDown(binding.icCategoryDropDown)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}