package com.techkingsley.domain.models

import com.techkingsley.domain.models.news.News

data class NewsResult(val news: List<News>, val error: Throwable? = null)