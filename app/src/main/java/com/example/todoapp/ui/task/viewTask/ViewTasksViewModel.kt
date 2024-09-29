package com.example.todoapp.ui.task.viewTask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.TaskDao
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.model.Category
import com.example.todoapp.model.Task
import com.example.todoapp.repository.CategoryRepository
import com.example.todoapp.repository.TaskRepository
import kotlinx.coroutines.launch


class ViewTasksViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao: TaskDao = ToDoDatabase.getDatabase(application).taskDao()
    private val taskRepository: TaskRepository = TaskRepository(application)
    private val categoryRepository: CategoryRepository = CategoryRepository(application)
    val recentlyDeletedTasks: LiveData<List<Task>> = taskRepository.getRecentlyDeletedTasks()
    val categories: LiveData<List<Category>> = categoryRepository.getAllCategories()

    val activeTasks: LiveData<List<Task>> = taskDao.getAllActiveTasks()
    val deletedTasks: LiveData<List<Task>> = taskDao.getRecentlyDeletedTasks()
//
    val toDoTasks: LiveData<List<Task>> = taskRepository.getToDoTasks()
    val inProgressTasks: LiveData<List<Task>> = taskRepository.getInProgressTasks()
    val doneTasks: LiveData<List<Task>> = taskRepository.getDoneTasks()
//
    fun softDeleteTask(taskId: Long) = viewModelScope.launch {
        taskRepository.softDeleteTask(taskId)
    }
    class ViewTasksViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ViewTasksViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ViewTasksViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}