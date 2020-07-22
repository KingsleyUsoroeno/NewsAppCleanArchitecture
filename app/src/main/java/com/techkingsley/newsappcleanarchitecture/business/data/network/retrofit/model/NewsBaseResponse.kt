package com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model

import com.google.gson.annotations.SerializedName

data class NewsBaseResponse(
    val status: String,

    val totalResults: Int,

    @SerializedName("articles")
    val articles: List<NewsNetworkEntity>
)