package com.techkingsley.newsappcleanarchitecture.di

import com.techkingsley.data.repository.news.NewsRepositoryImpl
import com.techkingsley.domain.repositories.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}