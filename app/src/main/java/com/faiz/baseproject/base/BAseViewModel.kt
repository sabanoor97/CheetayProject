package com.faiz.baseproject.base

import androidx.lifecycle.ViewModel
import com.faiz.baseproject.utils.Constants

abstract class BaseViewModel : ViewModel() {

    protected fun getPagerDefaults(
        search: String = "",
        offset: String = "10",
        limit: String = "10",
        map: MutableMap<String, Any> = mutableMapOf()
    ): MutableMap<String, Any> {
        return map.apply {
            put("offset", offset)
            put("limit", limit)
            put(Constants.SEARCH, search)
        }
    }
}