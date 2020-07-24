package com.techkingsley.newsappcleanarchitecture.business.data.cache.model

import androidx.room.PrimaryKey

data class BookMarkNews(
    val author: String = "",
    val title: String = "",
    val description: String = "",
    @PrimaryKey val newsUrl: String = "",
    val urlToImage: String = ""
)