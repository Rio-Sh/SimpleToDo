package com.example.studymytaskapp.taskdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.studymytaskapp.R
import com.example.studymytaskapp.databinding.TaskDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {

    private val viewModel: TaskDetailViewModel by viewModels()
    private lateinit var binding: TaskDetailFragmentBinding
    private val args: TaskDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TaskDetailFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val taskId = args.taskIdString?.toLongOrNull()
        viewModel.start(taskId)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO Refactor here to be more simple.
        //Just observe because task is a Cold Observable.
        viewModel.task.observe(viewLifecycleOwner, Observer {  })

        viewModel.navigateToTaskFrag.observe(viewLifecycleOwner, {
            navigateToTasks()
        })
    }

    private fun navigateToTasks() {
        val action = TaskDetailFragmentDirections.actionTaskDetailFragmentToTasksFragment()
        findNavController().navigate(action)
    }
}