package com.techkingsley.remote.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val status: String,

    val totalResults: Int,

    @SerializedName("articles")
    val articles: List<NewsNetworkEntity>
)