package com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers

import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.PoliticalNews
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsNetworkEntity
import com.techkingsley.newsappcleanarchitecture.business.domain.util.EntityMapper
import javax.inject.Inject

class PoliticalNewsMapper @Inject constructor() : EntityMapper<NewsNetworkEntity, PoliticalNews> {

    override fun mapFromEntity(entity: NewsNetworkEntity): PoliticalNews {
        return PoliticalNews(
            author = entity.authorName ?: "",
            title = entity.title,
            description = entity.description,
            newsUrl = entity.newsUrl,
            urlToImage = entity.urlToImage ?: ""
        )
    }

    override fun mapToEntity(domainModel: PoliticalNews): NewsNetworkEntity {
        return NewsNetworkEntity(
            authorName = domainModel.author,
            title = domainModel.title,
            description = domainModel.description,
            newsUrl = domainModel.newsUrl,
            urlToImage = domainModel.urlToImage
        )
    }

    fun mapFromEntityList(entities: List<NewsNetworkEntity>): List<PoliticalNews> {
        return entities.map { mapFromEntity(it) }
    }
}