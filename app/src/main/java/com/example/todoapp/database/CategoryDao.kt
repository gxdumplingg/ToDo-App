package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoapp.model.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category): Long

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM category_table ORDER BY category_title ASC")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM category_table WHERE id = :categoryId LIMIT 1")
    fun getCategoryById(categoryId: Long): LiveData<Category?>
}
