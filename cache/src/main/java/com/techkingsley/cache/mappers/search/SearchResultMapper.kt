package com.techkingsley.cache.mappers.search

import com.techkingsley.cache.mappers.EntityMapper
import com.techkingsley.cache.model.CachedSearchHistory
import com.techkingsley.data.model.SearchHistoryEntity
import javax.inject.Inject

class SearchResultMapper @Inject constructor() : EntityMapper<CachedSearchHistory, SearchHistoryEntity> {

    override fun mapFromCached(type: CachedSearchHistory): SearchHistoryEntity {
        return SearchHistoryEntity(searchTitle = type.searchTitle)
    }

    override fun mapToCached(type: SearchHistoryEntity): CachedSearchHistory {
        return CachedSearchHistory(type.searchTitle)
    }
}
