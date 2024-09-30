package com.example.todoapp.ui.task.viewTask.detailedTask

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentDetailedTaskBinding
import com.example.todoapp.ui.dialog.CustomDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar
import java.util.Date
import java.util.Locale


class DetailedTaskFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailedTaskBinding? = null
    private val binding get() = _binding!!
    private val args: DetailedTaskFragmentArgs by navArgs()
    private var selectedCategoryId: Long = 0L
    private val viewModel: DetailedTaskViewModel by viewModels {
        DetailedTaskViewModel.DetailedTaskViewModelFactory(requireActivity().application)
    }
    private var selectedStatus: String = "To Do"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = (resources.displayMetrics.heightPixels * 0.5).toInt()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTaskById(args.id).observe(viewLifecycleOwner) { task ->
            if (task != null) {
                binding.title.setText(task.title)
                binding.description.setText(task.description)
                viewModel.categories.observe(viewLifecycleOwner) { categories ->
                    val category = categories.find { it.id == args.categoryId }
                    binding.category.text = category?.title ?: "Unknown Category"
                }
                binding.icCategoryDropdown.setOnClickListener{showCategoryDropdownMenu()}
                val dueDate = task.dueDate
                binding.tvDueDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dueDate)
                binding.icDelete.setOnClickListener {
                    showDeleteConfirmationDialog(args.id)
                }

                binding.tvDueDate.setOnClickListener { showDatePickerDialog(dueDate) }
                binding.icDueDateDropdown.setOnClickListener { showDatePickerDialog(dueDate) }

                binding.tvTimeStart.text = task.timeStart
                binding.tvTimeEnd.text = task.timeEnd

                binding.tvTimeStart.setOnClickListener { showTimePickerDialog(task.timeStart, true) }
                binding.icTimeStartDropdown.setOnClickListener { showTimePickerDialog(task.timeStart, true) }


                binding.tvTimeEnd.setOnClickListener { showTimePickerDialog(task.timeEnd, false) }
                binding.icTimeEndDropdown.setOnClickListener { showTimePickerDialog(task.timeEnd, false) }

                selectedStatus = task.status
                updateStatusTextView(task.status)
            }
        }

        binding.btnBack.setOnClickListener {
            showBackDialog()
        }

        binding.tvTodo.setOnClickListener { updateStatus("To Do") }
        binding.tvInProgress.setOnClickListener { updateStatus("In Progress") }
        binding.tvDone.setOnClickListener { updateStatus("Done") }

        binding.btnSave.setOnClickListener {
            showConfirmationDialog {
                saveUpdatedTask()
            }
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
        binding.category.text = categoryName
        selectedCategoryId = categoryId
        popupWindow.dismiss()
    }
    private fun updateStatusTextView(status: String) {
        binding.tvTodo.isSelected = status == "To Do"
        binding.tvInProgress.isSelected = status == "In Progress"
        binding.tvDone.isSelected = status == "Done"

        val selectedTextColor = ContextCompat.getColor(requireContext(), R.color.white)
        val defaultTextColor = ContextCompat.getColor(requireContext(), R.color.black)

        binding.tvTodo.setTextColor(if (binding.tvTodo.isSelected) selectedTextColor else defaultTextColor)
        binding.tvInProgress.setTextColor(if (binding.tvInProgress.isSelected) selectedTextColor else defaultTextColor)
        binding.tvDone.setTextColor(if (binding.tvDone.isSelected) selectedTextColor else defaultTextColor)
    }

    private fun updateStatus(newStatus: String) {
        selectedStatus = newStatus
        updateStatusTextView(newStatus) // Cập nhật lại UI
    }

    private fun saveUpdatedTask() {
        viewModel.getTaskById(args.id).observe(viewLifecycleOwner) { task ->
            if (task != null) {
                val updatedTask = task.copy(
                    title = binding.title.text.toString(),
                    description = binding.description.text.toString(),
                    status = selectedStatus
                )
                viewModel.updateTask(updatedTask)
                findNavController().popBackStack()
            }
        }
    }

    private fun showDatePickerDialog(currentDueDate: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = currentDueDate
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val dueDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }.time
                binding.tvDueDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dueDate)
                viewModel.updateTaskDueDate(args.id, dueDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun showTimePickerDialog(currentTime: String, isStartTime: Boolean) {
        val timeParts = currentTime.split(":").map { it.toInt() }
        val hour = timeParts[0]
        val minute = timeParts[1]

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val updatedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                if (isStartTime) {
                    binding.tvTimeStart.text = updatedTime
                    viewModel.updateTaskStartTime(args.id, updatedTime)
                } else {
                    binding.tvTimeEnd.text = updatedTime
                    viewModel.updateTaskEndTime(args.id, updatedTime)
                }
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }
    private fun showConfirmationDialog(onConfirm: () -> Unit) {
        val dialogView = layoutInflater.inflate(R.layout.confirmation_dialog, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
        dialogView.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            onConfirm()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showDeleteConfirmationDialog(taskId: Long) {
        val customDialog = CustomDialog(requireContext()).apply {
            message = "Are you sure you want to delete?"
            onConfirmClickListener = {
                viewModel.softDeleteTask(taskId)
                findNavController().popBackStack()
            }
        }
        customDialog.show()
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