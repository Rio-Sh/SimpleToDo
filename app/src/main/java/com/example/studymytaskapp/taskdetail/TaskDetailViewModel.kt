package com.example.studymytaskapp.taskdetail

import androidx.lifecycle.*
import com.example.studymytaskapp.data.ITaskRepository
import com.example.studymytaskapp.data.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.studymytaskapp.data.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val repository: ITaskRepository
): ViewModel() {

    //binding two-way
    val title = MutableLiveData<String>()
    val contents = MutableLiveData<String>()

    private val _taskId = MutableLiveData<Long>()

    private val _task = _taskId.switchMap { taskId ->
        repository.observeTask(taskId).map { checkLoadResult(it) }
    }
    val task: LiveData<Task?> = _task

    private var isNewTask = false

    private var isCompleted = false

    fun start(taskId: Long?) {
        if(taskId == null) {
            isNewTask = true
        } else {
            _taskId.value = taskId!!
        }
    }

    fun saveTask() {
        val currentTitle = title.value
        val currentContents = contents.value
        if(currentTitle == null || currentContents == null){
           return
        }

        if(isNewTask) {
            val task = Task(title = currentTitle, contents = currentContents)
            viewModelScope.launch {
                repository.saveTask(task)
            }
        } else {
            val task = Task(_taskId.value!!, currentTitle, currentContents, isCompleted)
            viewModelScope.launch {
                repository.updateTask(task)
            }
        }
    }

    private fun onTaskLoad(task: Task) {
        title.value = task.title
        contents.value = task.contents
        isCompleted = task.isCompleted
    }

    private fun checkLoadResult(taskResult: Result<Task>): Task? {
        return if (taskResult is Result.Success) {
            onTaskLoad(taskResult.data)
            taskResult.data
        } else {
            null
        }
    }
}