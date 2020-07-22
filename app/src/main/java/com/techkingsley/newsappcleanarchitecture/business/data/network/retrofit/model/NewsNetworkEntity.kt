package com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model

import com.google.gson.annotations.SerializedName

data class NewsNetworkEntity(
    @SerializedName("author")
    val authorName: String?,

    val title: String,

    val description: String,

    @SerializedName("url")
    val newsUrl: String,

    val urlToImage: String?
)