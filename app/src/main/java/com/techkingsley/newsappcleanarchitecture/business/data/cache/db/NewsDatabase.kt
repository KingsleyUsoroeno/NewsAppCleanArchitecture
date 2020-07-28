package com.techkingsley.newsappcleanarchitecture.business.data.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techkingsley.newsappcleanarchitecture.business.data.cache.dao.NewsDao
import com.techkingsley.newsappcleanarchitecture.business.data.cache.dao.SearchHistoryDao
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.News
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.SearchHistory

@Database(entities = [News::class, SearchHistory::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        const val DATABASE_NAME: String = "news_db"
    }
}