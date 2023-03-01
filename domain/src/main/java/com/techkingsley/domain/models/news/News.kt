package com.techkingsley.domain.models.news

import java.util.*

data class News(
    val category: String,
    val author: String?,
    val title: String?,
    val description: String?,
    val newsUrl: String?,
    val urlToImage: String?,
    val id: Long,
    val isBookmarked: Boolean,
    val bookmarkedTimestamp: Date
)