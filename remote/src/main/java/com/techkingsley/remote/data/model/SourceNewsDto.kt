package com.techkingsley.remote.data.model

import com.google.gson.annotations.SerializedName

data class SourceNewsResponseDto(
    @SerializedName("sources") val articles: List<SourceNewsDto>
)

data class SourceNewsDto(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
)