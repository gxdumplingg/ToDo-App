package com.example.todoapp.ui.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import com.example.todoapp.model.Category
import com.example.todoapp.model.Task
import com.example.todoapp.repository.CategoryRepository
import com.example.todoapp.repository.TaskRepository
import com.example.todoapp.ui.task.newtask.AddTaskViewModel
import java.util.Calendar
import java.util.Date

class CalendarViewModel(application: Application) : AndroidViewModel(application) {
    private val taskRepository: TaskRepository = TaskRepository(application)
    private val categoryRepository: CategoryRepository = CategoryRepository(application)
    val categories: LiveData<List<Category>> = categoryRepository.getAllCategories()

    fun getTasksByDueDate(selectedDate: Date): LiveData<List<Task>> {
        return taskRepository.getAllActiveTasks().map { tasks ->
            tasks.filter { task -> isTheSameDay(task.dueDate, selectedDate) }
        }
    }

    private fun isTheSameDay(dueDate: Date, selectedDate: Date): Boolean {
        val calendar1 = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()

        calendar1.time = dueDate
        calendar2.time = selectedDate

        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(
            Calendar.MONTH
        ) == calendar2.get(Calendar.MONTH) && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(
            Calendar.DAY_OF_MONTH
        ))

    }

    class CalendarViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CalendarViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}