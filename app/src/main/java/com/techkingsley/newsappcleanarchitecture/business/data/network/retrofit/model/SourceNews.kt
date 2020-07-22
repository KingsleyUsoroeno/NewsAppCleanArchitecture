package com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model

import com.google.gson.annotations.SerializedName

data class SourceNewsResponse(
    val status: String,
    @SerializedName("sources") val articles: List<SourceNews>
)

data class SourceNews(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
)