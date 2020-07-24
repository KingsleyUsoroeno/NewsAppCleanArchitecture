package com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers

import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.News
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsNetworkEntity
import com.techkingsley.newsappcleanarchitecture.business.domain.util.EntityMapper
import javax.inject.Inject

class NewsMapper @Inject constructor() : EntityMapper<NewsNetworkEntity, News> {

    override fun mapFromEntity(category: String, entity: NewsNetworkEntity): News {
        return News(
            category = category,
            author = entity.authorName ?: "",
            title = entity.title ?: "",
            description = entity.description ?: "",
            newsUrl = entity.newsUrl ?: "",
            urlToImage = entity.urlToImage ?: ""
        )
    }

    override fun mapToEntity(domainModel: News): NewsNetworkEntity {
        return NewsNetworkEntity(
            authorName = domainModel.author,
            title = domainModel.title,
            description = domainModel.description,
            newsUrl = domainModel.newsUrl,
            urlToImage = domainModel.urlToImage
        )
    }

    fun mapFromEntityList(category: String, entities: List<NewsNetworkEntity>): List<News> {
        return entities.map { mapFromEntity(category, it) }
    }
}