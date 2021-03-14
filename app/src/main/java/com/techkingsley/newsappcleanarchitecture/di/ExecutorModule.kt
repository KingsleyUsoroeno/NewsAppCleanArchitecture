package com.techkingsley.newsappcleanarchitecture.di

import com.ezike.tobenna.starwarssearch.domain.executor.PostExecutionThread
import com.techkingsley.newsappcleanarchitecture.executor.PostExecutionThreadImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface ExecutorModule {

    @get:[Binds Singleton]
    val PostExecutionThreadImpl.postExecutionThread: PostExecutionThread
}
