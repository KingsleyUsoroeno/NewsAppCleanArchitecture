package com.techkingsley.newsappcleanarchitecture.framework.ui.work

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.techkingsley.data.contract.cache.CacheNewsRepository
import com.techkingsley.data.contract.remote.NewsRemoteRepository
import com.techkingsley.presentation.from
import com.techkingsley.presentation.utils.NewsConstants
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import java.util.*

@HiltWorker
class RefreshDataWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val newsRemoteRepository: NewsRemoteRepository,
    private val cacheNewsRepository: CacheNewsRepository
) : CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            cacheNewsRepository.deleteAllNews()
            val techNewsEntity = newsRemoteRepository.fetchNewsByCategory(NewsConstants.TECH_NEWS, Date().from())
            cacheNewsRepository.insertNews(NewsConstants.TECH_NEWS, techNewsEntity)
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}