package com.techkingsley.presentation.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.techkingsley.domain.models.news.News
import com.techkingsley.domain.models.params.GetNewsParams
import com.techkingsley.domain.usecases.news.GetNewsByCategory
import com.techkingsley.domain.usecases.news.ObserveNews
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
class MovieViewModel @Inject constructor(
    private val getNews: GetNewsByCategory,
    private val observeNews: ObserveNews
) : BaseViewModel<NewsUiState>(initialState = NewsUiState.Idle) {

    private val newsCategory: String = NewsConstants.MOVIE_NEWS

    val news: LiveData<List<News>>
        get() = observeNews.execute(newsCategory).asLiveData()

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
                            setState(NewsUiState.Error("Failed to load your movie news"))
                        } else {
                            setState(NewsUiState.Success(result.news)) // emits the old data or cached data basically
                        }
                    }
                }.onStart {
                    setState(NewsUiState.Loading)

                }.catch { error ->
                    println("Throwable from fetching movie news is $error")
                    setState(NewsUiState.Error(error.message ?: ""))

                }.collect {}
        }
    }
}