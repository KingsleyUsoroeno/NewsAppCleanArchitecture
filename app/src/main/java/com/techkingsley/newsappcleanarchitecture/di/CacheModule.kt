package com.techkingsley.newsappcleanarchitecture.di

import android.content.Context
import androidx.room.Room
import com.techkingsley.newsappcleanarchitecture.business.data.cache.dao.MovieNewsDao
import com.techkingsley.newsappcleanarchitecture.business.data.cache.dao.PoliticalNewsDao
import com.techkingsley.newsappcleanarchitecture.business.data.cache.dao.TechnologyNewsDao
import com.techkingsley.newsappcleanarchitecture.business.data.cache.dao.TrendingNewsDao
import com.techkingsley.newsappcleanarchitecture.business.data.cache.db.NewsDatabase
import com.techkingsley.newsappcleanarchitecture.framework.datasource.cache.LocalCacheDataSource
import com.techkingsley.newsappcleanarchitecture.framework.datasource.cache.LocalCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
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
    fun provideTrendNewsDAO(newsDatabase: NewsDatabase): TrendingNewsDao {
        return newsDatabase.trendingNewsDao()
    }

    @Singleton
    @Provides
    fun providePoliticalNewsDao(newsDatabase: NewsDatabase): PoliticalNewsDao {
        return newsDatabase.politicalNewsDao()
    }

    @Singleton
    @Provides
    fun provideTechNewsDao(newsDatabase: NewsDatabase): TechnologyNewsDao {
        return newsDatabase.techNewsDao()
    }

    @Singleton
    @Provides
    fun provideMovieNewsDao(newsDatabase: NewsDatabase): MovieNewsDao {
        return newsDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideCacheDataSource(newsDatabase: NewsDatabase): LocalCacheDataSource {
        return LocalCacheDataSourceImpl(newsDatabase)
    }
}