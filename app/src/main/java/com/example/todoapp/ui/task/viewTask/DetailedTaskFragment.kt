package com.example.todoapp.ui.task.viewTask

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentDetailedTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.dialog.CustomDialog
import com.example.todoapp.viewmodel.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar
import java.util.Date
import java.util.Locale


class DetailedTaskFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailedTaskBinding? = null
    private val binding get() = _binding!!
    private val args: DetailedTaskFragmentArgs by navArgs()
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModel.AddTaskViewModelFactory(requireActivity().application)
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


        taskViewModel.getTaskById(args.id).observe(viewLifecycleOwner) { task ->
            if (task != null) {
                binding.title.setText(task.title)
                binding.description.setText(task.description)
                taskViewModel.categories.observe(viewLifecycleOwner) { categories ->
                    val category = categories.find { it.id == args.categoryId }
                    binding.category.setText(category?.title ?: "Unknown Category")
                }
                val dueDate = task.dueDate
                binding.tvDueDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dueDate)
                binding.icDelete.setOnClickListener {
                    showDeleteConfirmationDialog(task) // Hiển thị hộp thoại xác nhận
                }
                // Gọi showDatePickerDialog với giá trị dueDate hiện tại
                binding.tvDueDate.setOnClickListener {
                    showDatePickerDialog(dueDate)
                }
                binding.tvTimeStart.text = task.timeStart
                binding.tvTimeEnd.text = task.timeEnd

                binding.tvTimeStart.setOnClickListener {
                    showTimePickerDialog(task.timeStart, true)
                }

                binding.tvTimeEnd.setOnClickListener {
                    showTimePickerDialog(task.timeEnd, false)
                }

                selectedStatus = task.status
                updateStatusTextView(task.status)
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
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

    private fun updateStatusTextView(status: String) {
        binding.tvTodo.isSelected = status == "To Do"
        binding.tvInProgress.isSelected = status == "In Progress"
        binding.tvDone.isSelected = status == "Done"

        // Đổi màu chữ tùy theo trạng thái được chọn
        val selectedTextColor = ContextCompat.getColor(requireContext(), R.color.white)
        val defaultTextColor = ContextCompat.getColor(requireContext(), R.color.black)

        binding.tvTodo.setTextColor(if (binding.tvTodo.isSelected) selectedTextColor else defaultTextColor)
        binding.tvInProgress.setTextColor(if (binding.tvInProgress.isSelected) selectedTextColor else defaultTextColor)
        binding.tvDone.setTextColor(if (binding.tvDone.isSelected) selectedTextColor else defaultTextColor)
    }

    // Hàm cập nhật trạng thái được chọn
    private fun updateStatus(newStatus: String) {
        selectedStatus = newStatus
        updateStatusTextView(newStatus) // Cập nhật lại UI
    }

    private fun saveUpdatedTask() {
        taskViewModel.getTaskById(args.id).observe(viewLifecycleOwner) { task ->
            if (task != null) {
                val updatedTask = task.copy(
                    title = binding.title.text.toString(),
                    description = binding.description.text.toString(),
                    status = selectedStatus
                )
                taskViewModel.updateTask(updatedTask)
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
                taskViewModel.updateTaskDueDate(args.id, dueDate)
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
                // Cập nhật TextView với thời gian mới
                val updatedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                if (isStartTime) {
                    binding.tvTimeStart.text = updatedTime
                    // Cập nhật trạng thái trong ViewModel
                    taskViewModel.updateTaskStartTime(args.id, updatedTime)
                } else {
                    binding.tvTimeEnd.text = updatedTime
                    // Cập nhật trạng thái trong ViewModel
                    taskViewModel.updateTaskEndTime(args.id, updatedTime)
                }
            },
            hour,
            minute,
            true // true để sử dụng định dạng 24 giờ
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

    private fun showDeleteConfirmationDialog(task: Task) {
        val customDialog = CustomDialog(requireContext()).apply {
            message = "Are you sure you want to delete"
            onConfirmClickListener = {
                taskViewModel.deleteTask(task)
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