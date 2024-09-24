package com.example.todoapp.ui.task.viewTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.adapter.ViewPagerAdapter
import com.example.todoapp.databinding.FragmentViewTaskBinding
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
    }
}