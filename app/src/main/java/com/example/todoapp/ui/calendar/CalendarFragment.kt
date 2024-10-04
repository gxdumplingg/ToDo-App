package com.example.todoapp.ui.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener
import com.example.todoapp.adapter.CalendarTaskAdapter
import com.example.todoapp.databinding.FragmentCalendarBinding
import com.example.todoapp.model.Task
import java.util.*

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private lateinit var calendarTaskAdapter: CalendarTaskAdapter
    private val viewModel: CalendarViewModel by viewModels(){
        CalendarViewModel.CalendarViewModelFactory(requireActivity().application)
    }
    private lateinit var selectedDateTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        selectedDateTextView = binding.tvSelectedDate

        binding.calendarView.setOnCalendarDayClickListener(object : OnCalendarDayClickListener {
            override fun onClick(calendarDay: CalendarDay) {
                val selectedDate = calendarDay.calendar.time
                selectedDateTextView.text = formatDate(selectedDate)
                loadTasksForSelectedDate(selectedDate)
            }
        })
    }
    private fun setupRecyclerView() {
        calendarTaskAdapter = CalendarTaskAdapter { task ->
            onTaskClick(task)
        }
        binding.rvCalendarTask.apply {
            adapter = calendarTaskAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
    private fun formatDate(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
    }
    private fun loadTasksForSelectedDate(selectedDate: Date) {
        viewModel.getTasksByDueDate(selectedDate).observe(viewLifecycleOwner) { tasks ->
            if(tasks.isEmpty()){
                binding.tvNoTasks.visibility = View.VISIBLE
                binding.rvCalendarTask.visibility = View.GONE
            }
            else{
                binding.tvNoTasks.visibility = View.GONE
                binding.rvCalendarTask.visibility = View.VISIBLE
                calendarTaskAdapter.submitList(tasks)
            }
        }
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categories.forEach { category ->
                calendarTaskAdapter.updateTaskCategory(category.id, category.title)
            }
        }
    }
    private fun onTaskClick(task: Task) {
        val action = CalendarFragmentDirections.actionCalendarFragmentToDetailedTaskFragment(
            task.id,
            task.categoryId
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
