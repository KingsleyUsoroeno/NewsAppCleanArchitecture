package com.techkingsley.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techkingsley.cache.dao.BookMarkedNewsDao
import com.techkingsley.cache.dao.NewsDao
import com.techkingsley.cache.dao.SearchHistoryDao
import com.techkingsley.cache.model.CachedBookMarkNews
import com.techkingsley.cache.model.CachedNews
import com.techkingsley.cache.model.CachedSearchHistory

@Database(entities = [CachedNews::class, CachedSearchHistory::class, CachedBookMarkNews::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun bookMarkedNewsDao(): BookMarkedNewsDao

    companion object {
        const val DATABASE_NAME: String = "news_db"
    }
}