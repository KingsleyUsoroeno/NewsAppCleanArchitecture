package com.techkingsley.newsappcleanarchitecture.business.data.cache.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    val authorName: String?,

    val title: String?,

    val description: String?,
    
    val newsUrl: String?,

    val urlToImage: String?
)