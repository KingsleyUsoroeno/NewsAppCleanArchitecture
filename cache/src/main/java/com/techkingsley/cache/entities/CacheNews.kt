package com.techkingsley.cache.entities

import androidx.room.*
import com.techkingsley.cache.converter.DateConverter
import java.sql.Timestamp
import java.util.Date

@Entity(indices = [Index(value = ["title", "description"], unique = true)])
@TypeConverters(DateConverter::class)
data class CacheNews(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "newsCategory") val category: String = "",
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val newsUrl: String = "",
    val urlToImage: String = "",
    val isBookmarked: Boolean = false,
    val bookmarkedTimestamp: Date
)