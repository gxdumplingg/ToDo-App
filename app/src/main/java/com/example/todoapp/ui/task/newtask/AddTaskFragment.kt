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
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.PopupWindow
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.model.Category
import com.example.todoapp.model.Task
import com.example.todoapp.ui.dialog.SuccessDialog
import com.example.todoapp.ui.category.allCategories.CategoryViewModel
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private val taskViewModel: AddTaskViewModel by viewModels {
        AddTaskViewModel.AddTaskViewModelFactory(requireActivity().application)
    }
    private val categoryViewModel: CategoryViewModel by viewModels(){
        CategoryViewModel.CategoryViewModelFactory(requireActivity().application)
    }
    private var selectedCategoryId: Long = 0L
    private var selectedStatus: String = "To do"
    private var categories: List<Category> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCategories()
        setupClickListeners()
    }

    private fun loadCategories() {
        categoryViewModel.allCategories.observe(viewLifecycleOwner) { categoryList ->
            categories = categoryList
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

        val categoryContainer = popupView as LinearLayout
        categoryContainer.removeAllViews()

        categories.forEach { category ->
            val categoryView = LayoutInflater.from(requireContext()).inflate(R.layout.menu_category_dropdown, categoryContainer, false)
            val categoryTitle = categoryView.findViewById<TextView>(R.id.category_title)
            categoryTitle.text = category.title
            categoryView.setOnClickListener {
                updateCategory(category.title, category.id, popupWindow)
            }

            categoryContainer.addView(categoryView)
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
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
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
