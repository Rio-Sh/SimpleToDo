package com.example.studymytaskapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "taskid")
    var taskId: Long = 0L,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "contents")
    var contents: String = "",

    @ColumnInfo(name = "completed")
    var isCompleted: Boolean = false,

    @ColumnInfo(name = "update_date")
    var updateDate: Long = System.currentTimeMillis()
)