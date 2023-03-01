package com.techkingsley.data.model

import java.util.*

data class NewsEntity(
    val id: Long,
    val category: String,
    val author: String?,
    val title: String?,
    val description: String?,
    val newsUrl: String?,
    val urlToImage: String?,
    val isBookmarked : Boolean,
    val bookmarkedTimestamp: Date
)