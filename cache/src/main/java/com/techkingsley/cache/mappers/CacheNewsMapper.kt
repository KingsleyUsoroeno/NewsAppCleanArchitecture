package com.techkingsley.cache.mappers

import com.techkingsley.cache.entities.CacheNews
import com.techkingsley.cache.mappers.base.EntityMapper
import com.techkingsley.cache.mappers.base.NewsEntityMapper
import com.techkingsley.data.model.NewsEntity
import javax.inject.Inject

class CacheNewsMapper @Inject constructor() : EntityMapper<CacheNews, NewsEntity> {

    override fun mapFromCached(cache: CacheNews): NewsEntity {
        return NewsEntity(
            category = cache.category,
            author = cache.author,
            title = cache.title,
            description = cache.description,
            newsUrl = cache.newsUrl,
            urlToImage = cache.urlToImage,
            isBookmarked = cache.isBookmarked,
            id = cache.id,
            bookmarkedTimestamp = cache.bookmarkedTimestamp
        )
    }

    override fun mapToCached(entity: NewsEntity): CacheNews {
        return CacheNews(
            category = entity.category,
            author = entity.author ?: "",
            title = entity.title ?: "",
            description = entity.description ?: "",
            newsUrl = entity.newsUrl ?: "",
            urlToImage = entity.urlToImage ?: "",
            isBookmarked = entity.isBookmarked,
            id = entity.id,
            bookmarkedTimestamp = entity.bookmarkedTimestamp
        )
    }
}