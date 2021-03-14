package com.techkingsley.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CachedSearchHistory(@PrimaryKey val searchTitle: String)