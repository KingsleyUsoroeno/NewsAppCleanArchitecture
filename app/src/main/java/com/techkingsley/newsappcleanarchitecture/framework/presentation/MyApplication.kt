package com.techkingsley.newsappcleanarchitecture.framework.presentation

import android.app.Application
import com.facebook.stetho.Stetho
import com.techkingsley.newsappcleanarchitecture.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }
}