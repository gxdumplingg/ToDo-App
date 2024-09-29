package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.model.Task
import java.util.Date

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE status = :status")
    fun getTasksByStatus(status: String): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE id = :taskId LIMIT 1")
    fun getTaskById(taskId: Long): LiveData<Task>


    @Query("SELECT COUNT(*) FROM task_table WHERE status = 'In Progress'")
    fun getInProgressTaskCount(): LiveData<Int>

    @Query("UPDATE task_table SET status = :newStatus WHERE id = :taskId")
    suspend fun updateTaskStatus(taskId: Long, newStatus: String)

    @Query("UPDATE task_table SET dueDate = :newDueDate WHERE id = :taskId")
    suspend fun updateTaskDueDate(taskId: Long, newDueDate: Date)

    @Query("UPDATE task_table SET timeStart = :newStartTime WHERE id = :taskId")
    suspend fun updateTaskStartTime(taskId: Long, newStartTime: String)

    @Query("UPDATE task_table SET timeEnd = :newEndTime WHERE id = :taskId")
    suspend fun updateTaskEndTime(taskId: Long, newEndTime: String)

    @Query("SELECT * FROM task_table WHERE isDeleted = 1")
    fun getRecentlyDeletedTasks(): LiveData<List<Task>>
    @Query("UPDATE task_table SET isDeleted = 1 WHERE id = :taskId")
    suspend fun softDeleteTask(taskId: Long)
    @Query("UPDATE task_table SET isDeleted = 0 WHERE id = :taskId")
    suspend fun restoreTask(taskId: Long)

    @Query("SELECT * FROM task_table WHERE isDeleted = 0")
    fun getAllActiveTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE status = 'To Do' AND isDeleted = 0")
    fun getToDoTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE status = 'In Progress' AND isDeleted = 0")
    fun getInProgressTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE status = 'Done' AND isDeleted = 0")
    fun getDoneTasks(): LiveData<List<Task>>

}

