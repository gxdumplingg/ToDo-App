package com.example.todoapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.Category

class CategoryAdapter(
    private val categories: List<Category>,
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
//        val taskCount = categoryViewModel.getTaskCountForCategory(category.id)
//        val completedPercent = categoryViewModel.getCompletionPercentageForCategory(category.id)

        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        private val tvTaskCount: TextView = itemView.findViewById(R.id.tvTaskCount)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.rectangleProgressBar)
        private val cardView: CardView = itemView.findViewById(R.id.cardviewCategory)

        @SuppressLint("SetTextI18n")
        fun bind(category: Category){
            categoryName.text = category.title
//            tvTaskCount.text = "$taskCount Tasks"
            progressBar.progress = category.completedPercent.toInt()
            cardView.setCardBackgroundColor(category.color)
        }
    }
}
