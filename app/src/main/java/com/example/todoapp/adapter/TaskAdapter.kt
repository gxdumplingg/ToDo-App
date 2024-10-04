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
import com.example.todoapp.databinding.ItemTaskGridBinding
import com.example.todoapp.model.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskAdapter(private val onClick: (Task) -> Unit) :
    ListAdapter<Task, RecyclerView.ViewHolder>(TaskDiffCallback()) {

    private val taskCategoryMap = mutableMapOf<Long, String>()
    private var isGridLayout = false

    @SuppressLint("NotifyDataSetChanged")
    fun updateTaskCategory(categoryId: Long, categoryName: String) {
        taskCategoryMap[categoryId] = categoryName
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_task_grid) {
            val binding =
                ItemTaskGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            GridViewHolder(binding)
        } else {
            val binding =
                ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LinearViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = getItem(position)
        val categoryName = taskCategoryMap[task.categoryId] ?: "Unknown"

        when (holder) {
            is LinearViewHolder -> holder.bind(task, categoryName, onClick)
            is GridViewHolder -> holder.bind(task, categoryName, onClick)
        }
    }

    fun setIsGridLayout(isGrid: Boolean) {
        isGridLayout = isGrid
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGridLayout) {
            R.layout.item_task_grid
        } else {
            R.layout.item_task
        }
    }

    class LinearViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, categoryName: String, onClick: (Task) -> Unit) {
            val dateFormat = SimpleDateFormat("EE, dd MMM yyyy", Locale.getDefault())

            binding.taskTitle.text = task.title
            binding.taskCategory.text = categoryName
            binding.itemDueDate.text = dateFormat.format(task.dueDate)
            binding.itemStatus.text = task.status

            when (task.status) {
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

            binding.root.setOnClickListener { onClick(task) }
        }
    }

    class GridViewHolder(private val binding: ItemTaskGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, categoryName: String, onClick: (Task) -> Unit) {
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

            binding.tvTaskTitle.text = task.title
            binding.tvCategoryName.text = categoryName
            binding.tvDueDate.text = dateFormat.format(task.dueDate)
            binding.tvStatus.text = task.status

            when (task.status) {
                "To Do" -> {
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.red
                        )
                    )
                }

                "In Progress" -> {
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.bright_yellow
                        )
                    )
                }

                "Done" -> {
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.green
                        )
                    )
                }
            }

            binding.root.setOnClickListener { onClick(task) }
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
