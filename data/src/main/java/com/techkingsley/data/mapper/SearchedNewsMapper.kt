package com.techkingsley.data.mapper

import com.techkingsley.data.model.SearchHistoryEntity
import com.techkingsley.domain.entities.SearchHistory
import javax.inject.Inject

class SearchedNewsMapper @Inject constructor() : EntityMapper<SearchHistory, SearchHistoryEntity> {

    override fun mapFromDomain(type: SearchHistory): SearchHistoryEntity {
        return SearchHistoryEntity(searchTitle = type.searchTitle)
    }

    override fun mapToDomain(type: SearchHistoryEntity): SearchHistory {
        return SearchHistory(type.searchTitle)
    }
}