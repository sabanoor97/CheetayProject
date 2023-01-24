package com.faiz.baseproject.paging

import com.faiz.baseproject.base.BasePagingSource
import com.faiz.baseproject.di.IPagingSource
import kotlin.reflect.KClass

interface IPagingSourceFactory {
    fun getPagingSource(pagingSourceKey: KClass<out BasePagingSource<out Any, out Any>>): IPagingSource
}