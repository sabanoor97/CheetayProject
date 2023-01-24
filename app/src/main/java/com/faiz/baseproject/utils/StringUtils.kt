package com.faiz.baseproject.utils

import android.content.Context
import com.faiz.baseproject.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringUtils @Inject constructor(@ApplicationContext private val context: Context) {
    fun noNetworkErrorMessage() = context.getString(R.string.message_no_network_connected_str)
    fun somethingWentWrong() = context.getString(R.string.message_something_went_wrong_str)
}