package com.techkingsley.newsappcleanarchitecture.business.data.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techkingsley.newsappcleanarchitecture.business.data.cache.dao.MovieNewsDao
import com.techkingsley.newsappcleanarchitecture.business.data.cache.dao.PoliticalNewsDao
import com.techkingsley.newsappcleanarchitecture.business.data.cache.dao.TechnologyNewsDao
import com.techkingsley.newsappcleanarchitecture.business.data.cache.dao.TrendingNewsDao
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.Movies
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.PoliticalNews
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TechnologyNews
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TrendingNews

@Database(entities = [TechnologyNews::class, TrendingNews::class, PoliticalNews::class, Movies::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun techNewsDao(): TechnologyNewsDao
    abstract fun trendingNewsDao(): TrendingNewsDao
    abstract fun politicalNewsDao(): PoliticalNewsDao
    abstract fun movieDao(): MovieNewsDao

    companion object {
        const val DATABASE_NAME: String = "news_db"
    }
}