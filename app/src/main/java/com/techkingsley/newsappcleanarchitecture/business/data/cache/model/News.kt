package com.techkingsley.newsappcleanarchitecture.business.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
    @ColumnInfo(name = "newsCategory") val category: String = "",
    val author: String = "",
    val title: String = "",
    val description: String = "",
    @PrimaryKey val newsUrl: String = "",
    val urlToImage: String = ""
)