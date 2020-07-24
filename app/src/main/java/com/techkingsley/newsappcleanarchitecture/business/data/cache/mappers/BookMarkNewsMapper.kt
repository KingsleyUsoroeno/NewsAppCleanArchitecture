package com.techkingsley.newsappcleanarchitecture.business.data.cache.mappers

import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.BookMarkNews
import com.techkingsley.newsappcleanarchitecture.business.data.cache.model.News
import com.techkingsley.newsappcleanarchitecture.business.domain.util.EntityMapper
import javax.inject.Inject

class BookMarkNewsMapper @Inject constructor() : EntityMapper<BookMarkNews, News> {

    override fun mapFromEntity(category: String, entity: BookMarkNews): News {
        return News(
            category = category,
            author = entity.author ?: "",
            title = entity.title ?: "",
            description = entity.description ?: "",
            newsUrl = entity.newsUrl ?: "",
            urlToImage = entity.urlToImage ?: ""
        )
    }

    override fun mapToEntity(domainModel: News): BookMarkNews {
        return BookMarkNews(
            author = domainModel.author,
            title = domainModel.title,
            description = domainModel.description,
            newsUrl = domainModel.newsUrl,
            urlToImage = domainModel.urlToImage
        )
    }

}
