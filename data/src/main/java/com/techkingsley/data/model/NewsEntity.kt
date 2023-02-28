package com.techkingsley.data.model

data class NewsEntity(
    val id: Long,
    val category: String,
    val author: String?,
    val title: String?,
    val description: String?,
    val newsUrl: String?,
    val urlToImage: String?,
    val isBookmarked : Boolean
)