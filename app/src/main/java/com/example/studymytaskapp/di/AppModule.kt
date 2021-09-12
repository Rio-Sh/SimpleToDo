package com.example.studymytaskapp.di

import com.example.studymytaskapp.data.ITaskRepository
import com.example.studymytaskapp.data.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Singleton
    @Binds
    abstract fun bindITaskRepository(taskRepository : TaskRepository) : ITaskRepository
}
