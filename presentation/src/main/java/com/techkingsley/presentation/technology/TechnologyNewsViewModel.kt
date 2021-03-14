package com.techkingsley.presentation.technology

import com.techkingsley.domain.models.params.GetNewsParams
import com.techkingsley.domain.usecases.news.GetNewsByCategory
import com.techkingsley.presentation.BaseViewModel
import com.techkingsley.presentation.from
import com.techkingsley.presentation.newsstate.NewsUiState
import com.techkingsley.presentation.utils.NewsConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TechnologyNewsViewModel @Inject constructor(
    private val getNews: GetNewsByCategory
) : BaseViewModel<NewsUiState>(initialState = NewsUiState.Idle) {

    private val newsCategory: String = NewsConstants.TECH_NEWS

    init {
        launchOnUI {
            getNews.execute(GetNewsParams(newsCategory, Date().from))
                .map { result ->
                    val error: Throwable? = result.error
                    if (error == null) {
                        val news = result.news
                        if (news.isNullOrEmpty().not()) {
                            setState(NewsUiState.Success(news))
                        } else {
                            setState(NewsUiState.Empty)
                        }
                    } else {
                        if (result.news.isEmpty()) {
                            setState(NewsUiState.Error("Failed to load your Tech news please try again later"))
                        } else {
                            setState(NewsUiState.Success(result.news)) // emits the old data or cached data basically
                        }
                    }
                }.onStart {
                    setState(NewsUiState.Loading)

                }.catch { error ->
                    println("Throwable from fetching tech news is $error")
                    setState(NewsUiState.Error(error.message ?: "Please check your internet connection and try again"))

                }.collect {}
        }
    }
}