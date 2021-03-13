package com.techkingsley.newsappcleanarchitecture.di


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.techkingsley.data.contract.remote.NewsRemoteRepository
import com.techkingsley.newsappcleanarchitecture.BuildConfig
import com.techkingsley.remote.data.repository.NewsRemoteRepositoryImpl
import com.techkingsley.remote.service.NewsApiService
import dagger.Binds
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

    @get:[Binds Singleton]
    val NewsRemoteRepositoryImpl.newsRepository: NewsRemoteRepository

    companion object {
        @[Provides Singleton]
        fun provideGsonBuilder(): Gson {
            return GsonBuilder().create()
        }

        @[Provides Singleton]
        fun provideRetrofit(gson: Gson): Retrofit.Builder {
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            }

            val httpClient = OkHttpClient.Builder()
            httpClient.connectTimeout(30, TimeUnit.SECONDS)
            httpClient.readTimeout(30, TimeUnit.SECONDS)
            httpClient.addInterceptor(logging)
            val okHttpClient = httpClient.build()

            return Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
        }

        @[Provides Singleton]
        fun provideNewsApiService(retrofit: Retrofit.Builder): NewsApiService {
            return retrofit
                .build()
                .create(NewsApiService::class.java)
        }
    }
}






