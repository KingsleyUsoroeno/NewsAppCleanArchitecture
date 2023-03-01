package com.techkingsley.cache.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CacheSearchHistory(@PrimaryKey val searchTitle: String)