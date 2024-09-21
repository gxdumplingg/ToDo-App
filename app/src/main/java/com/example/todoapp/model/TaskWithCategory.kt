package com.example.todoapp.model

data class TaskWithCategory(
    val taskId: Int,
    val taskTitle: String,
    val dueDate: String,
    val timeStart: String,
    val timeEnd: String,
    val categoryId: Int,
    val status: Int,
    val priority: Int,
    val description: String,
    val isArchived: Boolean,
    val isDone: Boolean
)