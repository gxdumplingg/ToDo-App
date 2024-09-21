package com.example.todoapp.repository

import androidx.lifecycle.LiveData
import com.example.todoapp.database.CategoryDao
import com.example.todoapp.model.Category

class CategoryRepository(private val categoryDao: CategoryDao) {
    
    fun getAllCategories(): LiveData<List<Category>> {
        return categoryDao.getAllCategories()
    }
    
    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }
    
    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category)
    }
    
    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    fun insert(category: Category) {

    }
}