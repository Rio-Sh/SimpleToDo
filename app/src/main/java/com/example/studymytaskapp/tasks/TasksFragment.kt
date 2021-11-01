package com.example.studymytaskapp.tasks

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.studymytaskapp.R
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
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.tasks_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when(item.itemId) {
            R.id.menu_filter -> {
                showFilterPopupMenu()
                true
            }
            else -> false
        }

    private fun showFilterPopupMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_popup, menu)

            setOnMenuItemClickListener {
                viewModel.setFilterType(
                    when(it.itemId) {
                        R.id.active -> FilterType.ACTIVE_TASK
                        R.id.completed -> FilterType.COMPLETED_TASK
                        else -> FilterType.ALL_TASK
                    }
                )
                true
            }
            show()
        }
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