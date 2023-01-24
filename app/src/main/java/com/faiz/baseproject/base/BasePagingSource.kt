package com.faiz.baseproject.base

import androidx.paging.PagingSource

abstract class BasePagingSource<Key:Any, Response:Any> : PagingSource<Key, Response>() {
}