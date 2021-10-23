package com.example.studymytaskapp.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.studymytaskapp.databinding.TasksFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment() {

    private val viewModel: TasksViewModel by viewModels()

    private lateinit var binding: TasksFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TasksFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()
        setupNavigation()
    }

    private fun setupListAdapter() {
        val adapter = TasksListAdapter(TaskListClickListener { taskId ->
            val action = TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment(taskId.toString())
            findNavController().navigate(action)
        })
        binding.tasksList.adapter = adapter
    }

    private fun setupNavigation() {
        binding.floatingActionButton.setOnClickListener {
            val action = TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment()
            findNavController().navigate(action)
        }
    }
}