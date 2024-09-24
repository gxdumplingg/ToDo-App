package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoapp.model.Category
import com.example.todoapp.model.CategoryWithTaskCount

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category): Long

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    // Lấy tất cả các category
    @Query("SELECT * FROM category_table ORDER BY category_title ASC")
    fun getAllCategories(): LiveData<List<Category>>

    // Lấy category theo ID
    @Query("SELECT * FROM category_table WHERE id = :categoryId LIMIT 1")
    fun getCategoryById(categoryId: Long): LiveData<Category?>

    @Query("""
        SELECT c.*, 
               COUNT(t.id) as taskCount, 
               SUM(CASE WHEN t.status = 'Done' THEN 1 ELSE 0 END) * 100.0 / COUNT(t.id) AS completedPercent 
        FROM category_table c 
        LEFT JOIN task_table t ON c.id = t.categoryId 
        GROUP BY c.id
    """)
    fun getCategoryWithTaskCount(): LiveData<List<CategoryWithTaskCount>>
}
