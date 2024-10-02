package com.example.todoapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.model.Category
import com.example.todoapp.model.Task
import com.example.todoapp.repository.CategoryRepository
import com.example.todoapp.repository.TaskRepository

class HomeScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository: TaskRepository = TaskRepository(application)
    private val categoryRepository: CategoryRepository = CategoryRepository(application)

    val allTasks: LiveData<List<Task>> = taskRepository.getAllActiveTasks()
    val inProgressTasks: LiveData<List<Task>> = taskRepository.getInProgressTasks()
    val doneTasks: LiveData<List<Task>> = taskRepository.getDoneTasks()
    val categories: LiveData<List<Category>> = categoryRepository.allCategories


    val completionPercentage: LiveData<Int> = MediatorLiveData<Int>().apply {
        var totalTasks: List<Task>? = null
        var completedTasks: List<Task>? = null

        addSource(allTasks) { allTaskList ->
            totalTasks = allTaskList
            value = calculateCompletionPercentage(totalTasks, completedTasks)
        }

        addSource(doneTasks) { doneTaskList ->
            completedTasks = doneTaskList
            value = calculateCompletionPercentage(totalTasks, completedTasks)
        }
    }
    private fun calculateCompletionPercentage(allTasks: List<Task>?, doneTasks: List<Task>?): Int {
        return if (!allTasks.isNullOrEmpty()) {
            if (doneTasks.isNullOrEmpty()) {
                0
            } else {
                (doneTasks.size * 100) / allTasks.size
            }
        } else {
            0
        }
    }


    class HomeScreenViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeScreenViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
