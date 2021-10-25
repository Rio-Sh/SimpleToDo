package com.example.studymytaskapp.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.studymytaskapp.data.ITaskRepository
import com.example.studymytaskapp.data.Result
import com.example.studymytaskapp.data.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: ITaskRepository
): ViewModel() {

    private val tasks = repository.observeTasks().map { checkLoadResult(it) }

    private val stats = tasks.map { getTasksStates(tasks) }
    val activeTaskSize = stats.map { it?.activeTaskSize ?: 0 }
    val completedTaskSize = stats.map { it?.completedTaskSize ?: 0 }
    val completedTaskPercent = stats.map { it?.completedTaskPercent ?: 0f }

    private fun getTasksStates(tasks: LiveData<List<Task>>) : TaskStats? {
       return if (tasks.value == null || tasks.value.isNullOrEmpty()) {
           null
       } else {
           val numOfTasks = tasks.value!!.size
           val numOfActiveTask = tasks.value!!.count { !it.isCompleted }
           val numOfCompleted = numOfTasks - numOfActiveTask
           TaskStats (
               totalTaskSize = numOfTasks,
               activeTaskSize =  numOfActiveTask,
               completedTaskSize = numOfCompleted,
               completedTaskPercent = 100f * ( numOfCompleted / numOfTasks )
           )
       }
    }

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

    data class TaskStats(val totalTaskSize: Int, val activeTaskSize: Int, val completedTaskSize: Int, val completedTaskPercent: Float)
}