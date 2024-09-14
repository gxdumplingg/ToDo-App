package com.example.todoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.Category
import com.example.todoapp.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {

//    val allCategories: LiveData<List<Category>> = categoryRepository.getAllCategories()
//
//    fun insert(category: Category) = viewModelScope.launch {
//        categoryRepository.insertCategory(category)
//    }
//
//    fun update(category: Category) = viewModelScope.launch {
//        categoryRepository.updateCategory(category)
//    }
//
//    fun delete(category: Category) = viewModelScope.launch {
//        categoryRepository.deleteCategory(category)
//    }
}