package com.example.todoapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.Category
import com.example.todoapp.model.Task

class CategoryAdapter(
    private var categories: List<Category>,
    private var tasks: List<Task>,
    private val onCategoryClick: (Long) -> Unit,
    private val onMoreClick: (Long) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view, onCategoryClick, onMoreClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        val categoryTasks = tasks.filter { it.categoryId == category.id }
        val taskCount = categoryTasks.size
        val completedCount = categoryTasks.count { it.status == "Done" }
        holder.bind(category, taskCount, completedCount)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newCategories: List<Category>, newTasks: List<Task>) {
        categories = newCategories
        tasks = newTasks
        notifyDataSetChanged()
    }

    class CategoryViewHolder(
        itemView: View,
        private val onCategoryClick: (Long) -> Unit,
        private val onMoreClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val categoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        private val tvTaskCount: TextView = itemView.findViewById(R.id.tvTaskCount)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.rectangleProgressBar)
        private val cardView: CardView = itemView.findViewById(R.id.cardviewCategory)
        private val iconMore: ImageButton = itemView.findViewById(R.id.icMore)
        @SuppressLint("SetTextI18n")
        fun bind(category: Category, taskCount: Int, completedCount: Int) {
            categoryName.text = category.title
            tvTaskCount.text = "$taskCount Tasks"

            val completedPercent = if (taskCount > 0) {
                (completedCount.toFloat() / taskCount * 100).toInt()
            } else {
                0
            }
            progressBar.progress = completedPercent
            cardView.setCardBackgroundColor(category.color)

            itemView.setOnClickListener {
                onCategoryClick(category.id)
            }
            iconMore.setOnClickListener {
                onMoreClick(category.id)
            }
        }
    }
}
