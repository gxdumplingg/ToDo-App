package com.example.todoapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.todoapp.database.TaskDao
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.model.Task

class TaskRepository(context: Context) {
    private val taskDAO: TaskDao = ToDoDatabase.getDatabase(context).taskDao()
    suspend fun insertTask(task: Task) = taskDAO.insertTask(task)
    suspend fun updateTask(task: Task) = taskDAO.updateTask(task)
    suspend fun deleteTask(task: Task) = taskDAO.deleteTask(task)

    val allTasks: LiveData<List<Task>> = taskDAO.getAllTasks()
    fun getTasksByStatus(status: String): LiveData<List<Task>> {
        return taskDAO.getTasksByStatus(status)
    }

}