package com.techkingsley.cache.di

import android.content.Context
import com.techkingsley.cache.NewsDatabase
import com.techkingsley.cache.mappers.CacheMappers
import com.techkingsley.cache.mappers.CacheNewsMapper
import com.techkingsley.cache.mappers.CacheSearchResultMapper
import com.techkingsley.cache.repository.news.CacheNewsRepositoryImpl
import com.techkingsley.data.contract.cache.CacheNewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface CacheModule {

    companion object {

        @[Provides Singleton]
        fun provideCacheNewsRepository(newsDb: NewsDatabase, cacheMappers: CacheMappers): CacheNewsRepository =
            CacheNewsRepositoryImpl(
                cacheMappers = cacheMappers,
                newsDatabase = newsDb
            )

        @[Provides Singleton]
        fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
            return NewsDatabase.provideNewsDatabase(context)
        }

        @[Provides Singleton]
        fun provideCacheMappers(): CacheMappers {
            return CacheMappers(
                newsMapper = CacheNewsMapper(),
                searchResultMapper = CacheSearchResultMapper()
            )
        }
    }

}