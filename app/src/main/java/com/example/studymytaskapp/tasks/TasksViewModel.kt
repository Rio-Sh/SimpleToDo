package com.example.studymytaskapp.tasks

import androidx.lifecycle.*
import com.example.studymytaskapp.data.ITaskRepository
import com.example.studymytaskapp.data.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.studymytaskapp.data.Result
import kotlinx.coroutines.launch

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: ITaskRepository
): ViewModel() {

    private val _filterType =  MutableLiveData(FilterType.ACTIVE_TASK)

    private val _taskItems = _filterType.switchMap {
        repository.observeTasks().map { checkLoadResult(it) }
    }
    val taskItems: LiveData<List<Task>> = _taskItems

    fun setFilterType(type: FilterType) {
        _filterType.value = type
    }

    /**
     * Called on task_list_item checkbox
     */
    fun completeTask(task: Task) {
        viewModelScope.launch {
            if(task.isCompleted){
                repository.activateTask(task)
            } else {
                repository.completeTask(task)
            }
        }
    }

    private fun checkLoadResult(taskListResult: Result<List<Task>>): List<Task> {
        val taskToShow = ArrayList<Task>()

        //TODO Create message if loading is failed.
        if(taskListResult is Result.Success) {
            val taskList = taskListResult.data
            for (task in taskList) {
                when(_filterType.value) {
                    FilterType.ACTIVE_TASK -> if(!task.isCompleted) {
                        taskToShow.add(task)
                    }
                    FilterType.COMPLETED_TASK -> if(task.isCompleted) {
                        taskToShow.add(task)
                    }
                    else -> taskToShow.add(task)
                }
            }
        }
        return taskToShow
    }
}