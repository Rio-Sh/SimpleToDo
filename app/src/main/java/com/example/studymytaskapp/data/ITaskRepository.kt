package com.example.studymytaskapp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

interface ITaskRepository {
    fun observeTasks(): LiveData<Result<List<Task>>>
    fun observeTask(taskId: Long): LiveData<Result<Task>>

    @WorkerThread
    suspend fun getTasks(): Result<List<Task>>

    @WorkerThread
    suspend fun getTask(taskId: Long): Result<Task>

    @WorkerThread
    suspend fun deleteTasks()

    @WorkerThread
    suspend fun deleteTask(taskId: Long): Int

    @WorkerThread
    suspend fun saveTask(task: Task)

    @WorkerThread
    suspend fun completeTask(taskId: Long)

    @WorkerThread
    suspend fun completeTask(task: Task)

    @WorkerThread
    suspend fun activateTask(taskId: Long)

    @WorkerThread
    suspend fun activateTask(task: Task)

    @WorkerThread
    suspend fun updateTask(task: Task): Int

    @WorkerThread
    suspend fun deleteCompletedTasks(): Int
}