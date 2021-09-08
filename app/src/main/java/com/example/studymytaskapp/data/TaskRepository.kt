package com.example.studymytaskapp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(
    private val tasksDao: TasksDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : ITaskRepository {

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        return tasksDao.observeAllTask().map {
            Result.Success(it)
        }
    }

    override fun observeTask(taskId: Long): LiveData<Result<Task>> {
        return tasksDao.observeTaskById(taskId).map {
            Result.Success(it)
        }
    }

    @WorkerThread
    override suspend fun getTasks(): Result<List<Task>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(tasksDao.getAllTask())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    @WorkerThread
    override suspend fun getTask(taskId: Long): Result<Task> = withContext(ioDispatcher) {
        try {
            val task = tasksDao.getTaskById(taskId)
            if(task != null) {
                return@withContext Result.Success(task)
            } else {
                return@withContext Result.Error(Exception("Task not found!"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    @WorkerThread
    override suspend fun deleteTasks() = withContext(ioDispatcher) {
        tasksDao.deleteAllTasks()
    }

    @WorkerThread
    override suspend fun deleteTask(taskId: Long) = withContext(ioDispatcher) {
        tasksDao.deleteTaskById(taskId)
    }

    @WorkerThread
    override suspend fun saveTask(task: Task) = withContext(ioDispatcher) {
        tasksDao.insertTask(task)
    }

    @WorkerThread
    override suspend fun completeTask(taskId: Long) = withContext(ioDispatcher) {
        tasksDao.updateCompletedTask(taskId, true)
    }

    @WorkerThread
    override suspend fun completeTask(task: Task) = withContext(ioDispatcher) {
        tasksDao.updateCompletedTask(task.taskId, true)
    }

    @WorkerThread
    override suspend fun activateTask(taskId: Long) = withContext(ioDispatcher) {
        tasksDao.updateCompletedTask(taskId, false)
    }
    @WorkerThread
    override suspend fun activateTask(task: Task) = withContext(ioDispatcher) {
        tasksDao.updateCompletedTask(task.taskId, false)
    }

    @WorkerThread
    override suspend fun updateTask(task: Task) = withContext(ioDispatcher) {
        tasksDao.updateTask(task)
    }

    @WorkerThread
    override suspend fun deleteCompletedTasks() = withContext(ioDispatcher) {
        tasksDao.deleteCompletedTask()
    }
}