package com.example.todoapp.ui.task.newtask

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.PopupWindow
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.model.Category
import com.example.todoapp.model.Task
import com.example.todoapp.ui.dialog.SuccessDialog
import com.example.todoapp.viewmodel.CategoryViewModel
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private val taskViewModel: AddTaskViewModel by viewModels {
        AddTaskViewModel.AddTaskViewModelFactory(requireActivity().application)
    }
    private val categoryViewModel: CategoryViewModel by viewModels()
    private var selectedCategoryId: Long = 0L
    private var selectedStatus: String = "To do"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCategories()
        setupClickListeners()
    }

    private fun setCategories() {
        val categories = listOf(
            Category(id = 1L, title = "Work", completedPercent = 0f),
            Category(id = 2L, title = "Education", completedPercent = 0f),
            Category(id = 3L, title = "Entertainment", completedPercent = 0f),
            Category(id = 4L, title = "Personal", completedPercent = 0f)
        )

        categories.forEach { category ->
            categoryViewModel.addCategory(category)
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            icCategoryDropdown.setOnClickListener { showCategoryDropdownMenu() }
            tvSelectedCategory.setOnClickListener{ showCategoryDropdownMenu() }
            icDueDateDropdown.setOnClickListener { showDatePickerDialog() }
            tvSelectedDueDate.setOnClickListener { showDatePickerDialog() }
            icTimeStartDropdown.setOnClickListener { showTimePickerDialog(true) }
            tvTimeStart.setOnClickListener { showTimePickerDialog(true) }
            icTimeEndDropdown.setOnClickListener { showTimePickerDialog(false) }
            tvTimeEnd.setOnClickListener { showTimePickerDialog(false) }
            btnAddTask.setOnClickListener { addTaskToDatabase() }
            btnBack.setOnClickListener { findNavController().popBackStack() }

            // Set up status selection
            tvTodo.setOnClickListener { selectStatus(tvTodo) }
            tvInProgress.setOnClickListener { selectStatus(tvInProgress) }
            tvDone.setOnClickListener { selectStatus(tvDone) }
        }
    }

    private fun showCategoryDropdownMenu() {
        val popupView = layoutInflater.inflate(R.layout.menu_category_dropdown, null)
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupView.apply {
            findViewById<TextView>(R.id.menu_item_work).setOnClickListener {
                updateCategory("Work", 1L, popupWindow)
            }
            findViewById<TextView>(R.id.menu_item_education).setOnClickListener {
                updateCategory("Education", 2L, popupWindow)
            }
            findViewById<TextView>(R.id.menu_item_entertainment).setOnClickListener {
                updateCategory("Entertainment", 3L, popupWindow)
            }
            findViewById<TextView>(R.id.menu_item_personal).setOnClickListener {
                updateCategory("Personal", 4L, popupWindow)
            }
        }

        popupWindow.showAsDropDown(binding.icCategoryDropdown)
    }

    private fun updateCategory(categoryName: String, categoryId: Long, popupWindow: PopupWindow) {
        binding.tvSelectedCategory.text = categoryName
        selectedCategoryId = categoryId
        popupWindow.dismiss()
    }

    private fun selectStatus(selectedTextView: TextView) {
        binding.apply {
            tvTodo.isSelected = false
            tvInProgress.isSelected = false
            tvDone.isSelected = false
        }


        selectedTextView.isSelected = true
        selectedStatus = when (selectedTextView.id) {
            R.id.tvTodo -> "To Do"
            R.id.tvInProgress -> "In Progress"
            R.id.tvDone -> "Done"
            else -> "To do"
        }
    }

    private fun addTaskToDatabase() {
        val title = binding.etTitle.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()
        val dueDateString = binding.tvSelectedDueDate.text.toString()
        val timeStart = binding.tvTimeStart.text.toString()
        val timeEnd = binding.tvTimeEnd.text.toString()

        if (title.isNotBlank()) {
            val dueDate = stringToDate(dueDateString)

            val newTask = Task(
                title = title,
                dueDate = dueDate ?: Date(),
                timeStart = timeStart,
                timeEnd = timeEnd,
                categoryId = selectedCategoryId,
                status = selectedStatus,
                description = description,
                isDeleted = false
            )

            taskViewModel.addTask(newTask)
            val successDialog = SuccessDialog(requireContext()).apply {
                message = "Successfully added"
            }
            successDialog.show()
            findNavController().popBackStack()
        }
    }


    private fun stringToDate(dateString: String): Date? {
        return try {
            val format = SimpleDateFormat("EE, dd MMM yyyy", Locale.getDefault())
            format.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    private fun showDatePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }
                val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
                binding.tvSelectedDueDate.text = dateFormat.format(selectedDate.time)
            }, year, month, day)

        datePickerDialog.setTitle("Select Date")
        datePickerDialog.show()
    }

    @SuppressLint("DefaultLocale")
    private fun showTimePickerDialog(isStartTime: Boolean) {
        val calendar: Calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                val timeString = String.format("%02d:%02d", selectedHour, selectedMinute)

                if (isStartTime) {
                    binding.tvTimeStart.text = timeString
                } else {
                    binding.tvTimeEnd.text = timeString
                }
            }, hour, minute, false)

        timePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
