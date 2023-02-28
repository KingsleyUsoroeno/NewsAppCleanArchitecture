package com.techkingsley.newsappcleanarchitecture.di


import com.techkingsley.data.contract.remote.NewsRemoteRepository
import com.techkingsley.newsappcleanarchitecture.BuildConfig
import com.techkingsley.remote.data.repository.NewsRemoteRepositoryImpl
import com.techkingsley.remote.service.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    companion object {

        @[Provides Singleton]
        fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
            return retrofit.create(NewsApiService::class.java)
        }

        @[Provides Singleton]
        fun provideNewsRemoteRepository(
            newsApiService: NewsApiService
        ): NewsRemoteRepository {
            return NewsRemoteRepositoryImpl(
                newsApiService = newsApiService,
                apiKey = "ca4ae9f450a44a39bd7b77f9a8745450"
            )
        }

        @[Provides Singleton]
        fun provideRetrofit(): Retrofit {
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder().apply {
                connectTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)
                addInterceptor(logging)
            }.build()

            return Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}






