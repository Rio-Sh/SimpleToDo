package com.example.studymytaskapp.tasks

import androidx.lifecycle.*
import com.example.studymytaskapp.data.ITaskRepository
import com.example.studymytaskapp.data.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.studymytaskapp.data.Result

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: ITaskRepository
): ViewModel() {

    private val _taskItems = repository.observeTasks().map { checkLoadResult(it) }

    val taskItems: LiveData<List<Task>> = _taskItems

    private fun checkLoadResult(taskListResult: Result<List<Task>>): List<Task> {
        val taskToShow = ArrayList<Task>()

        //TODO Create message if loading is failed.
        if(taskListResult is Result.Success) {
            val taskList = taskListResult.data
            for (task in taskList) {
                taskToShow.add(task)
            }
        }
        return taskToShow
    }
}