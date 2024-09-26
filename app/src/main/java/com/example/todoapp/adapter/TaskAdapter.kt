package com.example.todoapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskAdapter(private val onClick: (Task) -> Unit) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    private val taskCategoryMap = mutableMapOf<Long, String>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateTaskCategory(categoryId: Long, categoryName: String) {
        taskCategoryMap[categoryId] = categoryName
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        val categoryName = taskCategoryMap[task.categoryId] ?: "Unknown"
        holder.bind(task, categoryName, onClick)
    }

    class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, categoryName: String, onClick: (Task) -> Unit) {
            val dateFormat = SimpleDateFormat("EE, dd MMM yyyy", Locale.getDefault())

            binding.taskTitle.text = task.title
            binding.taskCategory.text = categoryName

            binding.itemDueDate.text = dateFormat.format(task.dueDate)

            // Chỉnh sửa để format lại trạng thái
            val statusFormatted = formatStatus(task.status)
            binding.itemStatus.text = statusFormatted

            // Đặt màu sắc theo trạng thái
            when (statusFormatted) {
                "To Do" -> {
                    binding.itemStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red))
                }
                "In Progress" -> {
                    binding.itemStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.bright_yellow))
                }
                "Done" -> {
                    binding.itemStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.green))
                }
            }

            binding.root.setOnClickListener { onClick(task) }
        }

        // Hàm format lại trạng thái
        private fun formatStatus(status: String): String {
            return status.split(" ")
                .joinToString(" ") { it.capitalize() } // Chuyển chữ cái đầu tiên thành viết hoa
        }
    }


    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}

