package com.techkingsley.data.mapper

import com.techkingsley.data.model.SourceNewsEntity
import com.techkingsley.domain.models.news.SourcedNews
import javax.inject.Inject

class SourceNewsMapper @Inject constructor() : EntityMapper<SourcedNews, SourceNewsEntity> {

    override fun mapFromDomain(type: SourcedNews): SourceNewsEntity {
        return SourceNewsEntity(
            id = type.id,
            name = type.name,
            description = type.description,
            url = type.url,
            category = type.category,
            language = type.language,
            country = type.country
        )
    }

    override fun mapToDomain(type: SourceNewsEntity): SourcedNews {
        return SourcedNews(
            id = type.id,
            name = type.name,
            description = type.description,
            url = type.url,
            category = type.category,
            language = type.language,
            country = type.country
        )
    }
}