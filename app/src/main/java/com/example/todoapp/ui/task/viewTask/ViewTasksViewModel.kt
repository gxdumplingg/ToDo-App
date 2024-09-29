package com.example.todoapp.ui.task.viewTask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.model.Category
import com.example.todoapp.model.Task
import com.example.todoapp.repository.CategoryRepository
import com.example.todoapp.repository.TaskRepository


class ViewTasksViewModel(application: Application) : AndroidViewModel(application) {

    private val taskRepository: TaskRepository = TaskRepository(application)
    private val categoryRepository: CategoryRepository = CategoryRepository(application)
    val allTasks: LiveData<List<Task>> = taskRepository.allTasks
    val categories: LiveData<List<Category>> = categoryRepository.getAllCategories()


    val todoTasks: LiveData<List<Task>> = taskRepository.getTasksByStatus("To Do")

    val inProgressTasks: LiveData<List<Task>> = taskRepository.getTasksByStatus("In Progress")

    val doneTasks: LiveData<List<Task>> = taskRepository.getTasksByStatus("Done")

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