package com.faiz.baseproject.ext

import android.text.TextUtils
import android.util.Patterns

fun isInternetAccessible(): Boolean {
    return try {
        val command = "ping -c 1 google.com"
        Runtime.getRuntime().exec(command).waitFor() == 0
    } catch (e: Exception) {
        false
    }
    return true
}
