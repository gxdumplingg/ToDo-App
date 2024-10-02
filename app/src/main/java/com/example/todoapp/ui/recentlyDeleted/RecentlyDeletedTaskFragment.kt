package com.example.todoapp.ui.recentlyDeleted

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.adapter.RecentlyDeletedTaskAdapter
import com.example.todoapp.databinding.FragmentRecentlyDeletedTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.dialog.CustomDialog

class RecentlyDeletedTaskFragment : Fragment() {

    private var _binding: FragmentRecentlyDeletedTaskBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecentlyDeletedTaskViewModel by viewModels() {
        RecentlyDeletedTaskViewModel.RecentlyDeletedTaskViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentlyDeletedTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RecentlyDeletedTaskAdapter { task ->
            showConfirmationDialog(task)
        }

        binding.rvRecentlyDeletedTask.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        viewModel.recentlyDeletedTasks.observe(viewLifecycleOwner) { recentlyDeletedTasks ->
            if (recentlyDeletedTasks.isEmpty()) {
                binding.tvNoTasks.visibility = View.VISIBLE
                binding.rvRecentlyDeletedTask.visibility = View.GONE
            } else {
                binding.tvNoTasks.visibility = View.GONE
                binding.rvRecentlyDeletedTask.visibility = View.VISIBLE
                adapter.submitList(recentlyDeletedTasks)
            }
        }

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            adapter.updateCategories(categories)
        }
    }

    private fun showConfirmationDialog(task: Task) {
        val customDialog = CustomDialog(requireContext()).apply {
            message = "Are you sure you want to restore this task?"
            onConfirmClickListener = {
                viewModel.restoreTask(task.id)
            }
        }
        customDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
