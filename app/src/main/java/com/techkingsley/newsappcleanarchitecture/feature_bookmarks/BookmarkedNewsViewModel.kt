package com.techkingsley.newsappcleanarchitecture.feature_bookmarks

import androidx.lifecycle.ViewModel
import com.techkingsley.domain.models.news.News
import com.techkingsley.domain.repositories.NewsRepository
import com.techkingsley.newsappcleanarchitecture.utils.NewsConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BookmarkedNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val bookmarkedNews: Flow<List<News>>
        get() = newsRepository.observeBookmarkedNews()
}