package com.example.todoapp.ui.category.detailedCategory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import com.example.todoapp.model.Category
import com.example.todoapp.model.Task
import com.example.todoapp.repository.CategoryRepository
import com.example.todoapp.repository.TaskRepository

class CategoryWithTaskListViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository: TaskRepository = TaskRepository(application)
    private val categoryRepository: CategoryRepository = CategoryRepository(application)
    val categories: LiveData<List<Category>> = categoryRepository.allCategories
    fun getCategoryById(categoryId: Long): LiveData<Category?> {
        return categoryRepository.getCategoryById(categoryId)
    }

    fun getTasksForCategory(categoryId: Long): LiveData<List<Task>> {
        return taskRepository.getTasksForCategory(categoryId)
    }

    class CategoryWithTaskListViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryWithTaskListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CategoryWithTaskListViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
