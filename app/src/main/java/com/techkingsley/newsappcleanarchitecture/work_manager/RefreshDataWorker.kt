package com.techkingsley.newsappcleanarchitecture.work_manager

//@HiltWorker
//class RefreshDataWorker @AssistedInject constructor(
//    @Assisted context: Context,
//    @Assisted params: WorkerParameters,
//    private val newsRemoteRepository: NewsRemoteRepository,
//    private val cacheNewsRepository: CacheNewsRepository
//) : CoroutineWorker(context, params) {
//
//    companion object {
//        const val WORK_NAME = "RefreshDataWorker"
//    }
//
//    override suspend fun doWork(): Result {
//        return try {
//            cacheNewsRepository.deleteAllNews()
//            val techNewsEntity = newsRemoteRepository.fetchNewsByCategory(NewsConstants.TECH_NEWS, Date().from())
//            cacheNewsRepository.insertNews(NewsConstants.TECH_NEWS, techNewsEntity)
//            Result.success()
//        } catch (e: HttpException) {
//            Result.retry()
//        }
//    }
//}