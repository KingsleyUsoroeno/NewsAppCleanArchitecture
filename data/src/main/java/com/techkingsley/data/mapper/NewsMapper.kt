package com.techkingsley.data.mapper

import com.techkingsley.data.model.NewsEntity
import com.techkingsley.domain.models.news.News
import javax.inject.Inject

class NewsMapper @Inject constructor() : EntityMapper<News, NewsEntity> {

    override fun mapFromDomain(type: News): NewsEntity {
        return NewsEntity(
            category = type.category,
            author = type.author,
            title = type.title,
            description = type.description,
            newsUrl = type.newsUrl,
            urlToImage = type.urlToImage
        )
    }

    override fun mapToDomain(type: NewsEntity): News {
        return News(
            category = type.category,
            author = type.author,
            title = type.title,
            description = type.description,
            newsUrl = type.newsUrl,
            urlToImage = type.urlToImage
        )
    }
}