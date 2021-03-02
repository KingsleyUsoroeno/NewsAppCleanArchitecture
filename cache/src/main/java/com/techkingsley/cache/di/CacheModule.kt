package com.techkingsley.cache.di

import android.content.Context
import androidx.room.Room
import com.techkingsley.cache.dao.NewsDao
import com.techkingsley.cache.dao.SearchHistoryDao
import com.techkingsley.cache.db.NewsDatabase
import com.techkingsley.cache.repository.news.CacheRepositoryImpl
import com.techkingsley.cache.source.news.CacheDataSource
import com.techkingsley.cache.source.news.CacheDataSourceImpl
import com.techkingsley.data.repository.news.CacheNewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CacheModule {

    @Binds
    fun bindCacheDataSource(cacheDataSourceImpl: CacheDataSourceImpl): CacheDataSource

    @Binds
    fun bindCacheRepository(cacheRepositoryImpl: CacheRepositoryImpl): CacheNewsRepository

    companion object {

        @[Provides Singleton]
        fun provideBlogDb(@ApplicationContext context: Context): NewsDatabase {
            return Room.databaseBuilder(
                context, NewsDatabase::class.java,
                NewsDatabase.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        @[Provides Singleton]
        fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao {
            return newsDatabase.newsDao()
        }

        @[Provides Singleton]
        fun provideSearchHistoryDao(newsDatabase: NewsDatabase): SearchHistoryDao {
            return newsDatabase.searchHistoryDao()
        }
    }

}