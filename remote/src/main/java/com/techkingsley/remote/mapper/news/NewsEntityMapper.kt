package com.techkingsley.remote.mapper.news

import com.techkingsley.data.model.NewsEntity
import com.techkingsley.remote.data.model.NewsResponseDto
import java.util.*
import javax.inject.Inject

/**
 * Map a [NewsResponseDto] to and from a [NewsEntity] instance when data is moving between
 * this later and the Data layer
 */
class NewsEntityMapper @Inject constructor() : EntityMapper<NewsResponseDto, List<NewsEntity>> {

    override fun mapFromRemote(category: String, type: NewsResponseDto): List<NewsEntity> {
        return type.articles.map {
            NewsEntity(
                category = category,
                author = it.authorName,
                title = it.title,
                description = it.description,
                newsUrl = it.newsUrl,
                urlToImage = it.urlToImage,
                id = 0,
                isBookmarked = false,
                bookmarkedTimestamp = Date()
            )
        }
    }
}