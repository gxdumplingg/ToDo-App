package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task

class RecentlyDeletedTaskAdapter(private val onRestoreClick: (Task) -> Unit) : ListAdapter<Task, RecentlyDeletedTaskAdapter.ArchivedTaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchivedTaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArchivedTaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArchivedTaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, onRestoreClick)
    }

    class ArchivedTaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task, onRestoreClick: (Task) -> Unit) {
            binding.taskTitle.text = task.title
            binding.itemDueDate.text = task.description

            binding.itemDueDate.text = android.text.format.DateFormat.format("dd/MM/yyyy", task.dueDate)

//            // Xử lý sự kiện click cho nút Restore
//            binding.btnRestore.setOnClickListener {
//                onRestoreClick(task)
//            }
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