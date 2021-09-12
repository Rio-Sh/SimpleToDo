package com.example.studymytaskapp.statistics

import androidx.lifecycle.ViewModel
import com.example.studymytaskapp.data.ITaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: ITaskRepository
): ViewModel() {
    // TODO: Implement the ViewModel
}