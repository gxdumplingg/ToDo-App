package com.example.todoapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.model.Category
import com.example.todoapp.model.Task
import com.example.todoapp.repository.CategoryRepository
import com.example.todoapp.repository.TaskRepository

class HomeScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository: TaskRepository = TaskRepository(application)
    private val categoryRepository: CategoryRepository = CategoryRepository(application)


    val inProgressTasks: LiveData<List<Task>> = taskRepository.getTasksByStatus("In Progress")
    val categories: LiveData<List<Category>> = categoryRepository.getAllCategories()

    class HomeScreenViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeScreenViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}