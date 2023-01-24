package com.faiz.baseproject.utils

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApplication : Application() {

    companion object {
        var instance: MovieApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        MultiDex.install(this)
    }
}