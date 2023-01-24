package com.faiz.baseproject.di

import dagger.MapKey
import kotlin.reflect.KClass


@MapKey
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
annotation class PagingSourceKey(val value: KClass<out IPagingSource>)