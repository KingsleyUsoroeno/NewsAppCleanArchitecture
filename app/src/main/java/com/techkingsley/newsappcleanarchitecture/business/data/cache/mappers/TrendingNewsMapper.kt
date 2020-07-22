package com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers

import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.TrendingNews
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.model.NewsNetworkEntity
import com.techkingsley.newsappcleanarchitecture.business.domain.util.EntityMapper
import javax.inject.Inject

class TrendingNewsMapper @Inject
constructor() : EntityMapper<NewsNetworkEntity, TrendingNews> {
    override fun mapFromEntity(entity: NewsNetworkEntity): TrendingNews {
        return TrendingNews(
            author = entity.authorName ?: "",
            description = entity.description,
            newsUrl = entity.newsUrl,
            urlToImage = entity.urlToImage ?: ""
        )
    }

    override fun mapToEntity(domainModel: TrendingNews): NewsNetworkEntity {
        return NewsNetworkEntity(
            authorName = domainModel.author,
            title = domainModel.title,
            description = domainModel.description,
            newsUrl = domainModel.newsUrl,
            urlToImage = domainModel.urlToImage
        )
    }

    fun mapFromEntityList(entities: List<NewsNetworkEntity>): List<TrendingNews> {
        return entities.map { mapFromEntity(it) }
    }
}