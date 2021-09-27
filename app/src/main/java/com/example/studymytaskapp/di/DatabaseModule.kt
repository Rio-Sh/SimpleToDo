package com.example.studymytaskapp.di

import android.content.Context
import androidx.room.Room
import com.example.studymytaskapp.data.TasksDao
import com.example.studymytaskapp.data.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideTasksDao(toDoDatabase : ToDoDatabase) : TasksDao {
        return toDoDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun providerToDoDatabase(@ApplicationContext appContext: Context) : ToDoDatabase {
        return Room.databaseBuilder(
            appContext,
            ToDoDatabase::class.java,
            "TodoDatabase"
        ).build()
    }
}
