package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.model.Category
import com.example.todoapp.model.CategoryWithTasks

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM category_table")
    fun getAllCategories(): LiveData<List<Category>>

    // Lấy Category với các Task liên quan
    @Query("SELECT * FROM category_table WHERE category_id = :categoryId")
    fun getCategoryWithTasks(categoryId: Int): LiveData<CategoryWithTasks>

    // Tìm Category theo title
    @Query("SELECT * FROM category_table WHERE category_title LIKE '%' || :title || '%'")
    fun searchCategoryByTitle(title: String): LiveData<List<Category>>

    // Đếm số lượng Category
    @Query("SELECT COUNT(*) FROM category_table")
    fun getCategoryCount(): LiveData<Int>
}