package com.example.todoapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.todoapp.database.TaskDao
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.model.Task
import java.util.Date

class TaskRepository(context: Context) {
    private val taskDao: TaskDao = ToDoDatabase.getDatabase(context).taskDao()
    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    fun getTaskById(id: Long): LiveData<Task> {
        return taskDao.getTaskById(id)
    }

    suspend fun updateTaskDueDate(taskId: Long, newDueDate: Date) {
        taskDao.updateTaskDueDate(taskId, newDueDate)
    }

    suspend fun updateTaskStartTime(taskId: Long, newStartTime: String) {
        taskDao.updateTaskStartTime(taskId, newStartTime)
    }

    suspend fun updateTaskEndTime(taskId: Long, newEndTime: String) {
        taskDao.updateTaskEndTime(taskId, newEndTime)
    }
    fun getRecentlyDeletedTasks(): LiveData<List<Task>> {
        return taskDao.getRecentlyDeletedTasks()
    }
    suspend fun softDeleteTask(taskId: Long) {
        taskDao.softDeleteTask(taskId)
    }
    suspend fun restoreTask(taskId: Long) {
        taskDao.restoreTask(taskId)
    }
    fun getToDoTasks(): LiveData<List<Task>> {
        return taskDao.getToDoTasks()
    }

    fun getInProgressTasks(): LiveData<List<Task>> {
        return taskDao.getInProgressTasks()
    }

    fun getDoneTasks(): LiveData<List<Task>> {
        return taskDao.getDoneTasks()
    }
    fun getTaskByDueDate(dueDate: Date): LiveData<List<Task>>{
        return taskDao.getTasksByDueDate(dueDate)
    }
    fun getAllActiveTasks(): LiveData<List<Task>> {
        return taskDao.getAllActiveTasks()
    }
    fun getTasksForCategory(categoryId: Long): LiveData<List<Task>> {
        return taskDao.getTasksForCategory(categoryId)
    }

}