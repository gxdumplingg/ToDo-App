package com.example.todoapp.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.TaskDao
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.model.Category
import com.example.todoapp.model.Task
import com.example.todoapp.repository.CategoryRepository
import com.example.todoapp.repository.TaskRepository
import kotlinx.coroutines.launch

class AddTaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository: TaskRepository = TaskRepository(application)
    private val categoryRepository: CategoryRepository = CategoryRepository(application)
    val allTasks: LiveData<List<Task>> = taskRepository.allTasks
    private val taskDao: TaskDao = ToDoDatabase.getDatabase(application).taskDao()

    val categories: LiveData<List<Category>> = categoryRepository.getAllCategories()

    fun addTask(task: Task) = viewModelScope.launch {
        taskRepository.insertTask(task)
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun getCategoryNameById(categoryId: Long): LiveData<String?> {
        return categoryRepository.getCategoryById(categoryId).map { it?.title }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    fun getTasksByStatus(status: String): LiveData<List<Task>> {
        return taskRepository.getTasksByStatus(status)
    }

    val todoTasks: LiveData<List<Task>> = taskRepository.getTasksByStatus("To Do")

    val inProgressTasks: LiveData<List<Task>> = taskRepository.getTasksByStatus("In Progress")

    val doneTasks: LiveData<List<Task>> = taskRepository.getTasksByStatus("Done")

//    val inProgressTaskCount: LiveData<Int> = taskDao.getInProgressTaskCount()
    class AddTaskViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddTaskViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddTaskViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}