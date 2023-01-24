package com.faiz.baseproject.paging

import com.faiz.baseproject.base.BasePagingSource
import com.faiz.baseproject.di.IPagingSource
import com.faiz.baseproject.di.PagingSourceKey
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class PagingSourceFactory @Inject constructor(
//    private val pagingSourceMap: MutableMap<PagingSourceKey, Provider<IPagingSource>>
) {
//        override fun getPagingSource(pagingSourceKey: KClass<out BasePagingSource<out Any, out Any>>): IPagingSource {
//        return pagingSourceMap[pagingSourceKey]?.get()!!
//    }
}