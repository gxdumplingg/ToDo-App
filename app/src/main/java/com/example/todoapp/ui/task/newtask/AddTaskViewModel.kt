package com.example.todoapp.ui.task.newtask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.Task
import com.example.todoapp.repository.TaskRepository
import kotlinx.coroutines.launch


class AddTaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository: TaskRepository = TaskRepository(application)

    fun addTask(task: Task) = viewModelScope.launch {
        taskRepository.insertTask(task)
    }
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

