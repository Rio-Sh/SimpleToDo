package com.example.studymytaskapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TasksDao {

    @Query("SELECT * FROM Tasks")
    fun observeAllTask(): LiveData<List<Task>>

    @Query("SELECT * FROM Tasks WHERE taskid = :taskId")
    fun observeTaskById(taskId: Long): LiveData<Task>

    @Query("SELECT * FROM Tasks")
    suspend fun getAllTask(): List<Task>

    @Query("SELECT * FROM Tasks WHERE taskid = :taskId")
    suspend fun getTaskById(taskId: Long): Task

    /**
     * Delete a task by id
     *
     * @return The value is the number of rows affected by this query. It's always should be 1.
     */
    @Query("DELETE FROM Tasks WHERE taskid = :taskId")
    suspend fun deleteTaskById(taskId: Long):Int

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

    /**
     * Delete Completed tasks.
     * SQLite doesn't have Boolean Type. It's 0 or 1
     * @return the number of tasks deleted.
     */
    @Query("DELETE FROM tasks WHERE completed = 1")
    suspend fun deleteCompletedTask():Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(Task: Task)

    /**
     * @return The number of tasks affected. It's always to be 1.
     */
    @Update
    suspend fun updateTask(task: Task):Int

    @Query("UPDATE tasks SET completed = :completed WHERE taskid = :taskId")
    suspend fun updateCompletedTask(taskId: Long, completed: Boolean)

}
