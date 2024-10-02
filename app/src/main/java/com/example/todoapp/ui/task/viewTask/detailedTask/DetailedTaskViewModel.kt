package com.example.todoapp.ui.task.viewTask.detailedTask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.Category
import com.example.todoapp.model.Task
import com.example.todoapp.repository.CategoryRepository
import com.example.todoapp.repository.TaskRepository
import kotlinx.coroutines.launch
import java.util.Date


class DetailedTaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository: TaskRepository = TaskRepository(application)
    private val categoryRepository: CategoryRepository = CategoryRepository(application)
    val categories: LiveData<List<Category>> = categoryRepository.allCategories

    fun getTaskById(taskId: Long): LiveData<Task> {
        return taskRepository.getTaskById(taskId)
    }
    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }
    fun updateTaskDueDate(taskId: Long, newDueDate: Date) {
        viewModelScope.launch {
            taskRepository.updateTaskDueDate(taskId, newDueDate)
        }
    }
    fun updateTaskStartTime(taskId: Long, newStartTime: String) {
        viewModelScope.launch {
            taskRepository.updateTaskStartTime(taskId, newStartTime)
        }
    }
    fun updateTaskEndTime(taskId: Long, newEndTime: String) {
        viewModelScope.launch {
            taskRepository.updateTaskEndTime(taskId, newEndTime)
        }
    }
    fun softDeleteTask(taskId: Long) {
        viewModelScope.launch {
            taskRepository.softDeleteTask(taskId)
        }
    }


    class DetailedTaskViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailedTaskViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailedTaskViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}