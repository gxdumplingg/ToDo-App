package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.todoapp.model.Category
import com.example.todoapp.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val categoryRepository: CategoryRepository = CategoryRepository(application)
    val allCategories: LiveData<List<Category>> = categoryRepository.getAllCategories()

    fun addCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.insertCategory(category)
        }
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

    fun getCategoryNameById(categoryId: Long): LiveData<Category?> {
        return categoryRepository.getCategoryById(categoryId)
    }
}