package com.techkingsley.data.contract.remote

import com.techkingsley.data.model.NewsEntity
import com.techkingsley.data.model.SourceNewsEntity

interface NewsRemoteRepository {

    suspend fun fetchTrendingNews(): List<SourceNewsEntity>

    suspend fun fetchNewsByCategory(category: String, from: String): List<NewsEntity>
}