package com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers

import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TechnologyNews
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsNetworkEntity
import com.techkingsley.newsappcleanarchitecture.business.domain.util.EntityMapper
import javax.inject.Inject

class TechNewsMapper @Inject constructor() : EntityMapper<NewsNetworkEntity, TechnologyNews> {

    override fun mapFromEntity(entity: NewsNetworkEntity): TechnologyNews {
        return TechnologyNews(
            author = entity.authorName ?: "",
            title = entity.title ?: "",
            description = entity.description ?: "",
            newsUrl = entity.newsUrl ?: "",
            urlToImage = entity.urlToImage ?: ""
        )
    }

    override fun mapToEntity(domainModel: TechnologyNews): NewsNetworkEntity {
        return NewsNetworkEntity(
            authorName = domainModel.author,
            title = domainModel.title,
            description = domainModel.description,
            newsUrl = domainModel.newsUrl,
            urlToImage = domainModel.urlToImage
        )
    }

    fun mapFromEntityList(entities: List<NewsNetworkEntity>): List<TechnologyNews> {
        return entities.map { mapFromEntity(it) }
    }
}