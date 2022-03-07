package com.abdullateif.meistersearch.di

import android.content.Context
import com.abdullateif.meistersearch.data.local.LocalDataSource
import com.abdullateif.meistersearch.data.local.TaskDao
import com.abdullateif.meistersearch.data.local.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providerTaskDB(@ApplicationContext context: Context): TaskDatabase {
        return TaskDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun providePhotoDao(taskDatabase: TaskDatabase): TaskDao {
        return taskDatabase.taskDao()
    }

    @Singleton
    @Provides
    fun provideRoomRepository(taskDao: TaskDao): LocalDataSource {
        return LocalDataSource(taskDao)
    }

}