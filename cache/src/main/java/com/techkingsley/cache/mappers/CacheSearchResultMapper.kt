package com.techkingsley.cache.mappers

import com.techkingsley.cache.mappers.base.EntityMapper
import com.techkingsley.cache.entities.CacheSearchHistory
import com.techkingsley.data.model.SearchHistoryEntity
import javax.inject.Inject

class CacheSearchResultMapper @Inject constructor() : EntityMapper<CacheSearchHistory, SearchHistoryEntity> {
    override fun mapFromCached(cache: CacheSearchHistory): SearchHistoryEntity {
        return SearchHistoryEntity(cache.searchTitle)
    }

    override fun mapToCached(entity: SearchHistoryEntity): CacheSearchHistory {
        return CacheSearchHistory(entity.searchTitle)
    }
}
