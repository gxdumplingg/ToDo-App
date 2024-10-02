package com.example.todoapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.todoapp.database.CategoryDao
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.model.Category
import com.example.todoapp.model.CategoryWithTaskCount
import com.example.todoapp.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepository(application: Application) {
    private val categoryDao: CategoryDao
    init {
        val database = ToDoDatabase.getDatabase(application)
        categoryDao = database.categoryDao()
    }

    val allCategories: LiveData<List<Category>> = categoryDao.getAllCategories()
    suspend fun insertCategory(category: Category) = categoryDao.insertCategory(category)
    suspend fun updateCategory(category: Category) = categoryDao.updateCategory(category)
    suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)

    fun getCategoryById(categoryId: Long): LiveData<Category?> {
        return categoryDao.getCategoryById(categoryId)
    }
}