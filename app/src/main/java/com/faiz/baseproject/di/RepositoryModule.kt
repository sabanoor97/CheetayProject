package com.faiz.baseproject.di

import com.faiz.baseproject.repository.IMovieRepository
import com.faiz.baseproject.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideMovieRepository(
        movieRepo: MovieRepository
    ): IMovieRepository
}