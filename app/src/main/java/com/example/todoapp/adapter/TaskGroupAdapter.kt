package com.example.todoapp.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.Category

class TaskGroupAdapter(private val taskGroups: List<Category>) : RecyclerView.Adapter<TaskGroupAdapter.TaskGroupViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskGroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_group, parent, false)
        return TaskGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskGroupViewHolder, position: Int) {
        val taskGroup = taskGroups[position]
        holder.bind(taskGroup)
    }

    override fun getItemCount(): Int {
        return taskGroups.size
    }

    class TaskGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        private val taskCount: TextView = itemView.findViewById(R.id.tvTaskCount)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.circularProgressBar)

        fun bind(taskGroup: Category) {
            categoryName.text = taskGroup.title
            taskCount.text = "${taskGroup.completedPercent} Tasks"
            progressBar.progress = taskGroup.completedPercent.toInt()
        }
    }
}
