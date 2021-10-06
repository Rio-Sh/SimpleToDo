package com.example.studymytaskapp.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studymytaskapp.data.Task
import com.example.studymytaskapp.databinding.TasksListItemBinding

class TasksListAdapter(val clickListener: TaskListClickListener) :
    ListAdapter<Task, TasksListAdapter.ViewHolder>(TaskDiffCallback()){

    class ViewHolder(private var binding: TasksListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: TaskListClickListener, task: Task) {
            binding.task = task
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
        holder.bind(clickListener, getItem(position))
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
