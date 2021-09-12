package com.example.studymytaskapp.tasks

import androidx.lifecycle.ViewModel
import com.example.studymytaskapp.data.ITaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: ITaskRepository
): ViewModel() {
    // TODO: Implement the ViewModel
}