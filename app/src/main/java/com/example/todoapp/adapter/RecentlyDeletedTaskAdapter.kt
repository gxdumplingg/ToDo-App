package com.example.todoapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemRecentlyDeletedTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.model.Category // Giả sử bạn có model Category

class RecentlyDeletedTaskAdapter(
    private val onRestoreClick: (Task) -> Unit
) : ListAdapter<Task, RecentlyDeletedTaskAdapter.RecentlyDeletedTaskAdapterViewHolder>(
    TaskDiffCallback()
) {

    private var categories: List<Category> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentlyDeletedTaskAdapterViewHolder {
        val binding = ItemRecentlyDeletedTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecentlyDeletedTaskAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentlyDeletedTaskAdapterViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, onRestoreClick, categories)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategories(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    class RecentlyDeletedTaskAdapterViewHolder(private val binding: ItemRecentlyDeletedTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task, onRestoreClick: (Task) -> Unit, categories: List<Category>) {
            binding.taskTitle.text = task.title
            binding.itemDueDate.text = android.text.format.DateFormat.format("dd/MM/yyyy", task.dueDate)
            val category = categories.find { it.id == task.categoryId }
            binding.taskCategory.text = category?.title ?: "Unknown"

            binding.icRestore.setOnClickListener {
                onRestoreClick(task)
            }

            val statusFormatted = task.status
            binding.itemStatus.text = statusFormatted

            when (statusFormatted) {
                "To Do" -> {
                    binding.itemStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.red
                        )
                    )
                }
                "In Progress" -> {
                    binding.itemStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.bright_yellow
                        )
                    )
                }
                "Done" -> {
                    binding.itemStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.green
                        )
                    )
                }
            }
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
