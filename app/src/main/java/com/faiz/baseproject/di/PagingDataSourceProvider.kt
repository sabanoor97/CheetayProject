package com.faiz.baseproject.di

import com.faiz.baseproject.paging.IMoviePagingSource
import com.faiz.baseproject.paging.IPagingSourceFactory
import com.faiz.baseproject.paging.MoviesPagingSource
import com.faiz.baseproject.paging.PagingSourceFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class PagingDataSourceProvider {

    @Binds
    abstract fun bindsPagingSourceFactory(pagingSourceFactory: PagingSourceFactory): IPagingSourceFactory

    @Binds
    @IntoMap
    @PagingSourceKey(MoviesPagingSource::class)
    abstract fun bindsMoviePagingInterface(pagingSource: IMoviePagingSource): IPagingSource

    @Binds
    abstract fun bindsMoviePagingSource(pagingSource: MoviesPagingSource): IMoviePagingSource
}