package com.techkingsley.newsappcleanarchitecture.di

import com.techkingsley.newsappcleanarchitecture.business.interactors.FetchNews
import com.techkingsley.newsappcleanarchitecture.business.interactors.FetchNewsImpl
import com.techkingsley.newsappcleanarchitecture.framework.datasource.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun provideFetchNews(networkDataSource: RemoteDataSource): FetchNews {
        return FetchNewsImpl(remoteDataSource = networkDataSource)
    }
}














