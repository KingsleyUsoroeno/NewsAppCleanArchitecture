package com.techkingsley.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["title","description"], unique = true)])
data class CacheNews(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "newsCategory") val category: String = "",
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val newsUrl: String = "",
    val urlToImage: String = "",
    val isBookmarked : Boolean = false
)