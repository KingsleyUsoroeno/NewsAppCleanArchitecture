package com.techkingsley.newsappcleanarchitecture.business.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistory(@PrimaryKey val searchTitle: String)