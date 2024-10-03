package com.example.todoapp.ui.task.viewTask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.Category
import com.example.todoapp.model.Task
import com.example.todoapp.repository.CategoryRepository
import com.example.todoapp.repository.TaskRepository
import kotlinx.coroutines.launch

class ViewTasksViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository: TaskRepository = TaskRepository(application)
    private val categoryRepository: CategoryRepository = CategoryRepository(application)
    val categories: LiveData<List<Category>> = categoryRepository.allCategories

    private val _activeTasks = MutableLiveData<List<Task>>()
    val activeTasks: LiveData<List<Task>> get() = _activeTasks

    private val _toDoTasks = MutableLiveData<List<Task>>()
    val toDoTasks: LiveData<List<Task>> get() = _toDoTasks
    private val _inProgressTasks = MutableLiveData<List<Task>>()
    val inProgressTasks: LiveData<List<Task>> get() = _inProgressTasks
    private val _doneTasks = MutableLiveData<List<Task>>()
    val doneTasks: LiveData<List<Task>> get() = _doneTasks

    init {
        viewModelScope.launch {
            taskRepository.getAllActiveTasks().observeForever { tasks ->
                _activeTasks.value = tasks
                _toDoTasks.value = tasks.filter { it.status == "To Do" }
                _inProgressTasks.value = tasks.filter { it.status == "In Progress" }
                _doneTasks.value = tasks.filter { it.status == "Done" }
            }
        }
    }

    fun sortAllTasksByDueDateAscending() {
        _activeTasks.value = _activeTasks.value?.sortedBy { it.dueDate }
    }

    fun sortAllTasksByDueDateDescending() {
        _activeTasks.value = _activeTasks.value?.sortedByDescending { it.dueDate }
    }

    fun sortToDoTasksByDueDateAscending() {
        _toDoTasks.value = _toDoTasks.value?.sortedBy { it.dueDate }
    }
    fun sortToDoTasksByDueDateDescending() {
        _toDoTasks.value = _toDoTasks.value?.sortedByDescending { it.dueDate }
    }

    fun sortInProgressTasksByDueDateAscending() {
        _inProgressTasks.value = _inProgressTasks.value?.sortedBy { it.dueDate }
    }
    fun sortInProgressTasksByDueDateDescending() {
        _inProgressTasks.value = _inProgressTasks.value?.sortedByDescending { it.dueDate }
    }
    fun sortDoneTasksByDueDateAscending() {
        _doneTasks.value = _doneTasks.value?.sortedBy { it.dueDate }
    }
    fun sortDoneTasksByDueDateDescending() {
        _doneTasks.value = _doneTasks.value?.sortedByDescending { it.dueDate }
    }

//    val toDoTasks: LiveData<List<Task>> = taskRepository.getToDoTasks()
//    val inProgressTasks: LiveData<List<Task>> = taskRepository.getInProgressTasks()
//    val doneTasks: LiveData<List<Task>> = taskRepository.getDoneTasks()

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
