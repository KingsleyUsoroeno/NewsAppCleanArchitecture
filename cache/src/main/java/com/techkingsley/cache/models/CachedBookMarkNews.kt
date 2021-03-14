package com.techkingsley.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CachedBookMarkNews(
    val author: String = "",
    val title: String = "",
    val description: String = "",
    @PrimaryKey val newsUrl: String = "",
    val urlToImage: String = ""
)