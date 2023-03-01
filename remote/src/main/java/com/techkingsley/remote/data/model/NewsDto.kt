package com.techkingsley.remote.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponseDto(

    @SerializedName("articles")
    val articles: List<NewsDto>
)

data class NewsDto(
    @SerializedName("author")
    val authorName: String?,

    val title: String?,

    val description: String?,

    @SerializedName("url")
    val newsUrl: String?,

    val urlToImage: String?
)