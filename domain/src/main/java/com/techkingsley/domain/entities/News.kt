package com.techkingsley.domain.entities

data class News(
    val category: String,
    val author: String?,
    val title: String?,
    val description: String?,
    val newsUrl: String?,
    val urlToImage: String? = ""
)