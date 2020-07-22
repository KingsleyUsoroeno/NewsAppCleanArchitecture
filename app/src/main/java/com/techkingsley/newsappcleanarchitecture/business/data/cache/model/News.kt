package com.techkingsley.newsappcleanarchitecture.business.data.cache.model

import androidx.room.PrimaryKey

open class News(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val newsUrl: String = "",
    val urlToImage: String = ""
)