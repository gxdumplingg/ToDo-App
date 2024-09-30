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

class CalendarTaskAdapter(private val onClick: (Task) -> Unit) : ListAdapter<Task, CalendarTaskAdapter.CalendarTaskViewHolder>(TaskDiffCallback()) {
    private val taskCategoryMap = mutableMapOf<Long, String>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateTaskCategory(categoryId: Long, categoryName: String) {
        taskCategoryMap[categoryId] = categoryName
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarTaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarTaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarTaskViewHolder, position: Int) {
        val task = getItem(position)
        val categoryName = taskCategoryMap[task.categoryId] ?: "Unknown"
        holder.bind(task, categoryName, onClick)
    }

    class CalendarTaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, categoryName: String, onClick: (Task) -> Unit) {
            val dateFormat = SimpleDateFormat("EE, dd MMM yyyy", Locale.getDefault())
            binding.taskTitle.text = task.title
            binding.itemDueDate.text = dateFormat.format(task.dueDate)
            binding.taskCategory.text = categoryName
            val statusFormatted = formatStatus(task.status)
            binding.itemStatus.text = statusFormatted

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
        private fun formatStatus(status: String): String {
            return status.split(" ")
                .joinToString(" ") { it.capitalize() }
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