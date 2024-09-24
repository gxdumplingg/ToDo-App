package com.example.todoapp.ui.task.viewTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentDetailedTaskBinding
import com.example.todoapp.viewmodel.AddTaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DetailedTaskFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailedTaskBinding? = null
    private val binding get() = _binding!!
    private val args: DetailedTaskFragmentArgs by navArgs()
    private val taskViewModel: AddTaskViewModel by viewModels {
        AddTaskViewModel.AddTaskViewModelFactory(requireActivity().application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val layoutParams = it.layoutParams
            layoutParams.height = (resources.displayMetrics.heightPixels * 2) / 3
            it.layoutParams = layoutParams
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnBack.setOnClickListener{
                findNavController().popBackStack()
            }

            tvTitle.text = args.title
            tvDescription.text = args.description
            taskViewModel.categories.observe(viewLifecycleOwner) { categories ->
                val category = categories.find { it.id == args.categoryId }
                tvCategory.text = category?.title ?: "Unknown Category"
            }

            val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDueDate = dateFormatter.format(Date(args.dueDate))
            tvDueDate.text = formattedDueDate

            tvStatus.text = args.status
            tvTimeStart.text = args.timeStart
            tvTimeEnd.text = args.timeEnd
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}