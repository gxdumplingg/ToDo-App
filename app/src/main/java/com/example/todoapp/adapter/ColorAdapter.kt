package com.example.todoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemColorOptionBinding

class ColorAdapter(
    private val context: Context,
    private val colors: List<Int>,
    private val onColorSelected: (Int) -> Unit
) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    private var selectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val binding = ItemColorOptionBinding.inflate(LayoutInflater.from(context), parent, false)
        return ColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colors[position]
        holder.bind(color, position == selectedPosition)
        holder.itemView.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition
            if (selectedPosition != currentPosition && currentPosition != RecyclerView.NO_POSITION) {
                val previousSelectedPosition = selectedPosition
                selectedPosition = currentPosition
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(currentPosition)
                onColorSelected(color)
            }
        }
    }


    override fun getItemCount(): Int = colors.size

    inner class ColorViewHolder(private val binding: ItemColorOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(color: Int, isSelected: Boolean) {
            binding.colorCircle.backgroundTintList = ContextCompat.getColorStateList(context, color)
            binding.checkmarkIcon.visibility = if (isSelected) View.VISIBLE else View.GONE
        }
    }
}