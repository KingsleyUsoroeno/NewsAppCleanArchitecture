package com.techkingsley.newsappcleanarchitecture.business.data.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techkingsley.newsappcleanarchitecture.business.data.cache.dao.*
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.*

@Database(entities = [TechnologyNews::class, TrendingNews::class, PoliticalNews::class, Movies::class, SearchHistory::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun techNewsDao(): TechnologyNewsDao
    abstract fun trendingNewsDao(): TrendingNewsDao
    abstract fun politicalNewsDao(): PoliticalNewsDao
    abstract fun movieDao(): MovieNewsDao
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        const val DATABASE_NAME: String = "news_db"
    }
}