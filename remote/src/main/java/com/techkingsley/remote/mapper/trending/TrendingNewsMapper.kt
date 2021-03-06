package com.techkingsley.remote.mapper.trending

import com.techkingsley.data.model.SourceNewsEntity
import com.techkingsley.remote.data.model.SourceNewsResponse
import javax.inject.Inject

class TrendingNewsMapper @Inject constructor() : TrendingEntityMapper<SourceNewsResponse, List<SourceNewsEntity>> {

    override fun mapFromRemote(type: SourceNewsResponse): List<SourceNewsEntity> {
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