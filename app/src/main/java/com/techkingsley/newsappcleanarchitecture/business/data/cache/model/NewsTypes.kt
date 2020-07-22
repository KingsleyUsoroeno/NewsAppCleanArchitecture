package com.techkingsley.newsappcleanarchitecture.business.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TechnologyNews(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val newsUrl: String = "",
    val urlToImage: String = ""
)

@Entity
data class TrendingNews(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val newsUrl: String = "",
    val urlToImage: String = ""
)

@Entity
data class PoliticalNews(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val newsUrl: String = "",
    val urlToImage: String = ""
)

@Entity
data class Movies(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val newsUrl: String = "",
    val urlToImage: String = ""
)