package com.example.todoapp.ui.recentlyDeleted

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

class RecentlyDeletedTaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository: TaskRepository = TaskRepository(application)
    private val categoryRepository: CategoryRepository = CategoryRepository(application)
    val recentlyDeletedTasks: LiveData<List<Task>> = taskRepository.getRecentlyDeletedTasks()

    val categories: LiveData<List<Category>> = categoryRepository.allCategories
    fun restoreTask(taskId: Long) {
        viewModelScope.launch {
            taskRepository.restoreTask(taskId)
        }
    }

    class RecentlyDeletedTaskViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecentlyDeletedTaskViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RecentlyDeletedTaskViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}