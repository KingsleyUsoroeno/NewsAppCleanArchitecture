package com.techkingsley.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.techkingsley.cache.dao.NewsDao
import com.techkingsley.cache.dao.SearchHistoryDao
import com.techkingsley.cache.entities.CacheNews
import com.techkingsley.cache.entities.CacheSearchHistory

@Database(
    entities = [CacheNews::class, CacheSearchHistory::class], version = 1, exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        private const val DATABASE_NAME: String = "news_db"

        fun provideNewsDatabase(context: Context): NewsDatabase {
            return Room.databaseBuilder(
                context, NewsDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}