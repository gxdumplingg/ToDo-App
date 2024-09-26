package com.example.todoapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.todoapp.database.TaskDao
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.model.Task
import java.util.Date

class TaskRepository(context: Context) {
    private val taskDAO: TaskDao = ToDoDatabase.getDatabase(context).taskDao()
    suspend fun insertTask(task: Task) = taskDAO.insertTask(task)
    suspend fun updateTask(task: Task) = taskDAO.updateTask(task)
    suspend fun deleteTask(task: Task) = taskDAO.deleteTask(task)

    val allTasks: LiveData<List<Task>> = taskDAO.getAllTasks()
    fun getTasksByStatus(status: String): LiveData<List<Task>> {
        return taskDAO.getTasksByStatus(status)
    }
    fun getTaskById(id: Long): LiveData<Task> {
        return taskDAO.getTaskById(id)
    }

    suspend fun updateTaskDueDate(taskId: Long, newDueDate: Date) {
        taskDAO.updateTaskDueDate(taskId, newDueDate)
    }

    suspend fun updateTaskStartTime(taskId: Long, newStartTime: String) {
        taskDAO.updateTaskStartTime(taskId, newStartTime)
    }

    suspend fun updateTaskEndTime(taskId: Long, newEndTime: String) {
        taskDAO.updateTaskEndTime(taskId, newEndTime)
    }

}