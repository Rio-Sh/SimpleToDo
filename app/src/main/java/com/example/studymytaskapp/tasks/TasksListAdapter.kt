package com.example.studymytaskapp.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studymytaskapp.data.Task
import com.example.studymytaskapp.databinding.TasksListItemBinding

// TODO Refactor the Adapter so that it does't have viewModel. This's bad practice leads to tight-coupling.
class TasksListAdapter(private val viewModel: TasksViewModel, val clickListener: TaskListClickListener) :
    ListAdapter<Task, TasksListAdapter.ViewHolder>(TaskDiffCallback()){

    class ViewHolder(private var binding: TasksListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind( viewModel: TasksViewModel, listener: TaskListClickListener, task: Task) {
            binding.task = task
            binding.viewModel = viewModel
            binding.taskTitle.text = task.title
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TasksListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, clickListener, getItem(position))
    }
}


class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}

class TaskListClickListener(val clickListener: (taskId: Long) -> Unit) {
    fun onClick(task: Task) = clickListener(task.taskId)
}
