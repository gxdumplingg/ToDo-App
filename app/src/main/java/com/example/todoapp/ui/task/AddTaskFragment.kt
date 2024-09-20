package com.example.todoapp.ui.task

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.PopupWindow
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.viewmodel.TaskViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Calendar
import java.util.Locale


class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TaskViewModel

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

        binding.icStatusDropdown.setOnClickListener {
            showStatusDropdownMenu()
        }

        binding.icPriorityDropdown.setOnClickListener {
            showPriorityDropdownMenu()
        }
        binding.icDueDateDropdown.setOnClickListener{
            showDatePickerDialog()
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showCategoryDropdownMenu() {
        val popupView = layoutInflater.inflate(R.layout.menu_category_dropdown, null)
        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

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

    private fun showStatusDropdownMenu() {
        val popupView = layoutInflater.inflate(R.layout.menu_status_dropdown, null)
        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        popupView.findViewById<TextView>(R.id.menu_item_to_do).setOnClickListener {
            binding.tvStatus.text = "To do"
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.menu_item_in_progress).setOnClickListener {
            binding.tvStatus.text = "In progress"
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.menu_item_done).setOnClickListener {
            binding.tvStatus.text = "Done"
            popupWindow.dismiss()
        }

        val location = IntArray(2)
        binding.icStatusDropdown.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1]

        popupWindow.showAtLocation(binding.root, Gravity.NO_GRAVITY, x, y - popupWindow.height)
    }

    private fun showPriorityDropdownMenu() {
        val popupView = layoutInflater.inflate(R.layout.menu_priority_dropdown, null)
        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        popupView.findViewById<TextView>(R.id.menu_item_low).setOnClickListener {
            binding.tvPriority.text = "Low"
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.menu_item_medium).setOnClickListener {
            binding.tvPriority.text = "Medium"
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.menu_item_high).setOnClickListener {
            binding.tvPriority.text = "High"
            popupWindow.dismiss()
        }

        val location = IntArray(2)
        binding.icPriorityDropdown.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1]

        popupWindow.showAtLocation(binding.root, Gravity.NO_GRAVITY, x, y - popupWindow.height)
    }

    private fun showDatePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = Calendar.getInstance().apply {
                set(selectedYear, selectedMonth, selectedDay)
            }
            val dateFormat = SimpleDateFormat("MMM-dd-yyyy", Locale.getDefault())
            binding.tvSelectedDueDate.text = dateFormat.format(selectedDate.time)
        }, year, month, day)

        datePickerDialog.setTitle("Select Date")
        datePickerDialog.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}