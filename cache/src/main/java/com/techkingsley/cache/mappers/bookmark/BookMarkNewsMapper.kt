package com.techkingsley.cache.mappers.bookmark

import com.techkingsley.cache.mappers.EntityMapper
import com.techkingsley.cache.model.CachedBookMarkNews
import com.techkingsley.data.model.BookMarkNewsEntity
import javax.inject.Inject

class BookMarkNewsMapper @Inject constructor() : EntityMapper<CachedBookMarkNews, BookMarkNewsEntity> {

    override fun mapFromCached(type: CachedBookMarkNews): BookMarkNewsEntity {
        return BookMarkNewsEntity(
            type.author,
            type.title,
            type.description,
            type.newsUrl,
            type.urlToImage
        )
    }

    override fun mapToCached(type: BookMarkNewsEntity): CachedBookMarkNews {
        return CachedBookMarkNews(
            type.author,
            type.title,
            type.description,
            type.newsUrl,
            type.urlToImage
        )
    }


}