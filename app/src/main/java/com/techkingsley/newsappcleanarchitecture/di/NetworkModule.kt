package com.techkingsley.newsappcleanarchitecture.di


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.techkingsley.newsappcleanarchitecture.BuildConfig
import com.techkingsley.newsappcleanarchitecture.business.data.cache.repository.NewsAppRepository
import com.techkingsley.newsappcleanarchitecture.business.data.network.retrofit.service.NewsApiService
import com.techkingsley.newsappcleanarchitecture.business.interactors.FetchNews
import com.techkingsley.newsappcleanarchitecture.framework.datasource.cache.LocalCacheDataSource
import com.techkingsley.newsappcleanarchitecture.framework.datasource.network.RemoteDataSource
import com.techkingsley.newsappcleanarchitecture.framework.datasource.network.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        }

        val httpClient = OkHttpClient.Builder()
        /*httpClient.addInterceptor { chain ->
            val original = chain.request()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .header("Authorization", "ca4ae9f450a44a39bd7b77f9a8745450") // <-- this is the important line

            val request = requestBuilder.build()
            chain.proceed(request)
        }*/

        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.addInterceptor(logging)
        val okHttpClient = httpClient.build()

        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideNewsApiService(retrofit: Retrofit.Builder): NewsApiService {
        return retrofit
            .build()
            .create(NewsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(service: NewsApiService): RemoteDataSource {
        return RemoteDataSourceImpl(newsApiService = service)
    }

    @Singleton
    @Provides
    fun provideNewsAppRepository(fetchNews: FetchNews, localCacheDataSource: LocalCacheDataSource): NewsAppRepository {
        return NewsAppRepository(fetchNews = fetchNews, localCacheDataSource = localCacheDataSource)
    }
}




















