package com.example.todoapp.ui.category.detailedCategory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.Category
import com.example.todoapp.repository.CategoryRepository
import com.example.todoapp.repository.TaskRepository
import com.example.todoapp.ui.task.viewTask.detailedTask.DetailedTaskViewModel
import kotlinx.coroutines.launch

class DetailedCategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val categoryRepository: CategoryRepository = CategoryRepository(application)
    private val taskRepository: TaskRepository = TaskRepository(application)

    val categories: LiveData<List<Category>> = categoryRepository.allCategories

    fun getCategoryById(categoryId: Long): LiveData<Category?> {
        return categoryRepository.getCategoryById(categoryId)
    }

    fun updateCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.updateCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
        }
    }
    class DetailedCategoryViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailedCategoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailedCategoryViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
