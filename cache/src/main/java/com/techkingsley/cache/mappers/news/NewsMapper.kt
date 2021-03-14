package com.techkingsley.cache.mappers.news

import com.techkingsley.cache.mappers.NewsEntityMapper
import com.techkingsley.cache.models.CachedNews
import com.techkingsley.data.model.NewsEntity
import javax.inject.Inject

class NewsMapper @Inject constructor() : NewsEntityMapper<CachedNews, NewsEntity> {

    override fun mapFromCached(category: String, type: CachedNews): NewsEntity {
        return NewsEntity(
            category = category,
            author = type.author,
            title = type.title,
            description = type.description,
            newsUrl = type.newsUrl,
            urlToImage = type.urlToImage
        )
    }

    override fun mapToCached(category: String, type: NewsEntity): CachedNews {
        return CachedNews(
            category = category,
            author = type.author ?: "",
            title = type.title ?: "",
            description = type.description ?: "",
            newsUrl = type.newsUrl ?: "",
            urlToImage = type.urlToImage ?: ""
        )
    }

    fun mapFromEntityList(category: String, entities: List<CachedNews>): List<NewsEntity> {
        return entities.map { mapFromCached(category, it) }
    }
}