package com.faiz.baseproject.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.faiz.baseproject.utils.MovieApplication
import com.faiz.baseproject.utils.StringUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    fun provideApplication(@ApplicationContext app: Context): MovieApplication {
        return app as MovieApplication
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(completableJob: CompletableJob) = CoroutineScope(completableJob)

    @Singleton
    @Provides
    fun providePref(@ApplicationContext app: Context): SharedPreferences {
        return app.getSharedPreferences("Yahuda", Context.MODE_PRIVATE)
    }

//    @Singleton
//    @Provides
//    fun providePrefManager(
//        sharedPreferences: SharedPreferences/*,
//        scope: CoroutineScope*/
//    ): IPrefManager {
//        return PrefManager(sharedPreferences/*, scope*/)
//    }

    @Singleton
    @Provides
    fun provideStringUtils(app: Application): StringUtils {
        return StringUtils(app)
    }
}