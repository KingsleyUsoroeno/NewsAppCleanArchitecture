package com.techkingsley.newsappcleanarchitecture.business.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val category: String = "",
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val newsUrl: String = "",
    val urlToImage: String = ""
)