package com.example.todoapp.ui.task.viewTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.adapter.ViewPagerAdapter
import com.example.todoapp.databinding.FragmentViewTaskBinding
import com.example.todoapp.ui.task.viewTask.tabTask.AllTasksFragment
import com.example.todoapp.ui.task.viewTask.tabTask.DoneTaskFragment
import com.example.todoapp.ui.task.viewTask.tabTask.InProgressTaskFragment
import com.example.todoapp.ui.task.viewTask.tabTask.ToDoTaskFragment
import com.google.android.material.tabs.TabLayoutMediator


class ViewTaskFragment : Fragment() {
    private lateinit var binding: FragmentViewTaskBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabTitles()
        binding.fabAddTask.setOnClickListener{
            binding.fabAddTask.setOnClickListener {
                findNavController().navigate(R.id.action_viewTaskFragment_to_addTaskFragment)
            }
        }
    }
    private fun setUpTabTitles(){
        val fragments = listOf(
            AllTasksFragment(),
            ToDoTaskFragment(),
            InProgressTaskFragment(),
            DoneTaskFragment()
        )
        val tabTitles = listOf("All", "To Do", "In Progress", "Done")
        viewPagerAdapter = ViewPagerAdapter(requireActivity(), fragments)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        customTabWidth()
    }

    private fun customTabWidth(){
        binding.tabLayout.post {
            val tabLayout = binding.tabLayout.getChildAt(0) as ViewGroup
            for (i in 0 until tabLayout.childCount) {
                val tab = tabLayout.getChildAt(i)
                val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
                when (i) {
                    0 -> layoutParams.width = (resources.displayMetrics.widthPixels * 0.15).toInt() // Tab All
                    1 -> layoutParams.width = (resources.displayMetrics.widthPixels * 0.3).toInt()  // Tab To Do
                    2 -> layoutParams.width = (resources.displayMetrics.widthPixels * 0.35).toInt()  // Tab In Progress
                    3 -> layoutParams.width = (resources.displayMetrics.widthPixels * 0.2).toInt() // Tab Done
                }
                tab.layoutParams = layoutParams
            }
        }
    }
}