package com.techkingsley.newsappcleanarchitecture.framework.presentation.work

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.techkingsley.newsappcleanarchitecture.business.data.cache.db.NewsDatabase
import com.techkingsley.newsappcleanarchitecture.business.data.cache.repository.NewsAppRepository
import com.techkingsley.newsappcleanarchitecture.framework.presentation.movies.MovieViewModel
import com.techkingsley.newsappcleanarchitecture.framework.presentation.politics.PoliticalNewsViewModel
import com.techkingsley.newsappcleanarchitecture.framework.presentation.technology.TechnologyNewsViewModel
import com.techkingsley.newsappcleanarchitecture.framework.presentation.trending.TrendingNewsViewModel
import retrofit2.HttpException
import javax.inject.Inject

class RefreshDataWorker @WorkerInject constructor(@Assisted appContext: Context, @Assisted params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    @Inject
    lateinit var newsAppRepository: NewsAppRepository

    @Inject
    lateinit var newsDatabase: NewsDatabase

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            clearNewsDatabase()
            newsAppRepository.refreshMovieNews(MovieViewModel.NEWS_CATEGORY)
            newsAppRepository.refreshPoliticalNews(PoliticalNewsViewModel.NEWS_CATEGORY)
            newsAppRepository.refreshTechNews(TechnologyNewsViewModel.NEWS_CATEGORY)
            newsAppRepository.refreshTrendingNews(TrendingNewsViewModel.NEWS_CATEGORY)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    private suspend fun clearNewsDatabase() {
        newsDatabase.newsDao().deleteAllNews()
    }
}