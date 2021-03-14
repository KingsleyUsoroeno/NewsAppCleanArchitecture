package com.techkingsley.presentation.politics

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
class PoliticalNewsViewModel @Inject constructor(
    private val getNews: GetNewsByCategory
) : BaseViewModel<NewsUiState>(initialState = NewsUiState.Idle) {

    private val newsCategory: String = NewsConstants.POLITICAL_NEWS

    private val from = Date().from

    init {
        launchOnUI {
            getNews.execute(GetNewsParams(newsCategory, from))
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
                            setState(NewsUiState.Error("Failed to load your political news"))
                        } else {
                            setState(NewsUiState.Success(result.news)) // emits the old data or cached data basically
                        }
                    }
                }.onStart {
                    setState(NewsUiState.Loading)

                }.catch { error ->
                    println("Throwable from fetching political news is $error")
                    setState(NewsUiState.Error(error.message ?: ""))

                }.collect {}
        }
    }
}