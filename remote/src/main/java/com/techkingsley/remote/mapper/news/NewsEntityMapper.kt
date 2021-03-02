package com.techkingsley.remote.mapper.news

import com.techkingsley.data.model.NewsEntity
import com.techkingsley.remote.data.model.NewsResponse
import javax.inject.Inject

/**
 * Map a [NewsResponse] to and from a [NewsEntity] instance when data is moving between
 * this later and the Data layer
 */
class NewsEntityMapper @Inject constructor() : EntityMapper<NewsResponse, List<NewsEntity>> {

    override fun mapFromRemote(category: String, type: NewsResponse): List<NewsEntity> {
        return type.articles.map {
            NewsEntity(
                category = category,
                author = it.authorName,
                title = it.title,
                description = it.description,
                newsUrl = it.newsUrl,
                urlToImage = it.urlToImage
            )
        }
    }
}