package com.techkingsley.remote.mapper.trending

import com.techkingsley.data.model.SourceNewsEntity
import com.techkingsley.remote.data.model.SourceNewsResponseDto
import javax.inject.Inject

class TrendingNewsMapper @Inject constructor() : TrendingEntityMapper<SourceNewsResponseDto, List<SourceNewsEntity>> {

    override fun mapFromRemote(type: SourceNewsResponseDto): List<SourceNewsEntity> {
        return with(type) {
            articles.map {
                SourceNewsEntity(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    url = it.url,
                    category = it.category,
                    language = it.language,
                    country = it.country
                )
            }
        }
    }
}