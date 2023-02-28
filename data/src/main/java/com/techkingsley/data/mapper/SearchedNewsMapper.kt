package com.techkingsley.data.mapper

import com.techkingsley.data.mapper.base.EntityMapper
import com.techkingsley.data.model.SearchHistoryEntity
import com.techkingsley.domain.models.searchhistory.SearchHistory
import javax.inject.Inject

class SearchedNewsMapper @Inject constructor() : EntityMapper<SearchHistory, SearchHistoryEntity> {

    override fun mapFromDomain(type: SearchHistory): SearchHistoryEntity {
        return SearchHistoryEntity(type.searchTitle)
    }

    override fun mapToDomain(type: SearchHistoryEntity): SearchHistory {
        return SearchHistory(type.searchTitle)
    }
}