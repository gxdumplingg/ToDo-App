package com.example.todoapp.ui.category.allCategories

import android.app.Application
import androidx.lifecycle.*
import com.example.todoapp.model.Category
import com.example.todoapp.repository.CategoryRepository
import com.example.todoapp.repository.TaskRepository
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository: TaskRepository = TaskRepository(application)
    private val categoryRepository: CategoryRepository = CategoryRepository(application)
    val allCategories: LiveData<List<Category>> = categoryRepository.allCategories

    fun addCategory(category: Category) = viewModelScope.launch {
        categoryRepository.insertCategory(category)
    }

    fun updateCategory(category: Category) = viewModelScope.launch {
        categoryRepository.updateCategory(category)
    }

    fun deleteCategory(category: Category) = viewModelScope.launch {
        categoryRepository.deleteCategory(category)
    }

    fun getTaskCountForCategory(categoryId: Long): Int {
        return taskRepository.getTaskCountByCategory(categoryId)
    }


    fun getCompletionPercentageForCategory(categoryId: Long): Float {
        return taskRepository.getCompletionPercentageForCategory(categoryId)
    }

    class CategoryViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CategoryViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
