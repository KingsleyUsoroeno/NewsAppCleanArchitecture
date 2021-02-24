package com.techkingsley.newsappcleanarchitecture.di

import android.content.Context
import androidx.room.Room
import com.techkingsley.cache.dao.NewsDao
import com.techkingsley.cache.dao.SearchHistoryDao
import com.techkingsley.cache.db.NewsDatabase
import com.techkingsley.cache.repository.news.CacheRepositoryImpl
import com.techkingsley.cache.source.news.CacheDataSource
import com.techkingsley.cache.source.news.CacheDataSourceImpl
import com.techkingsley.data.repository.news.CacheNewsRepository
import com.techkingsley.data.repository.news.NewsRemote
import com.techkingsley.data.repository.news.NewsRepositoryImpl
import com.techkingsley.domain.repositories.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideBlogDb(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context, NewsDatabase::class.java,
            NewsDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao {
        return newsDatabase.newsDao()
    }

    @Singleton
    @Provides
    fun provideSearchHistoryDao(newsDatabase: NewsDatabase): SearchHistoryDao {
        return newsDatabase.searchHistoryDao()
    }

    @Singleton
    @Provides
    fun provideCacheDataSource(newsDatabase: NewsDatabase): CacheDataSource {
        return CacheDataSourceImpl(newsDatabase)
    }

    @Singleton
    @Provides
    fun provideNewsLocalRepository(cacheDataSource: CacheDataSource): CacheNewsRepository {
        return CacheRepositoryImpl(cacheDataSource)
    }

    @Singleton
    @Provides
    fun provideNewsRepository(cacheNewsRepository: CacheNewsRepository, remoteNewsRepository: NewsRemote): NewsRepository {
        return NewsRepositoryImpl(cacheNewsRepository, remoteNewsRepository)
    }


}