package com.faiz.baseproject.utils

import android.content.Context
import com.faiz.baseproject.R

class CommonMethods {
    companion object {
        fun getErrorMessage(
            context: Context,
            errorCode: Int,
            errorMessage: String?,
            defaultMsg: String?
        ): String? {
            return when (errorCode) {
                in ResponseCode.SUCEESS_ERROR_MIN.value..ResponseCode.SUCEESS_ERROR_MAX.value -> errorMessage
                ResponseCode.CONNECTION_TIMEOUT.value -> context.getString(R.string.internet_error)
                ResponseCode.UNAUTHORIZED.value -> context.getString(R.string.user_not_found)
                ResponseCode.NOT_FOUND.value -> context.getString(R.string.user_not_found)
                ResponseCode.UNKNOWN.value -> context.getString(R.string.error_unknown)
                ResponseCode.NOCONTENT.value -> context.getString(R.string.no_content_found)
                else -> context.getString(R.string.message_something_went_wrong_str)
            }
        }
    }
}