package com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers

import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.Movies
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsNetworkEntity
import com.techkingsley.newsappcleanarchitecture.business.domain.util.EntityMapper
import javax.inject.Inject

class MovieNewsMapper @Inject constructor() : EntityMapper<NewsNetworkEntity, Movies> {

    override fun mapFromEntity(entity: NewsNetworkEntity): Movies {
        return Movies(
            author = entity.authorName ?: "",
            title = entity.title,
            description = entity.description,
            newsUrl = entity.newsUrl,
            urlToImage = entity.urlToImage ?: ""
        )
    }

    override fun mapToEntity(domainModel: Movies): NewsNetworkEntity {
        return NewsNetworkEntity(
            authorName = domainModel.author,
            title = domainModel.title,
            description = domainModel.description,
            newsUrl = domainModel.newsUrl,
            urlToImage = domainModel.urlToImage
        )
    }

    fun mapFromEntityList(entities: List<NewsNetworkEntity>): List<Movies> {
        return entities.map { mapFromEntity(it) }
    }
}