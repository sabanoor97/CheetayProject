package com.hexagram.yahuda.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

fun String.removeWhitespaces() = replace(" ", "")

fun EditText.onSearch(callback: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            clearFocus()
            callback.invoke()
            return@setOnEditorActionListener true
        }
        false
    }
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.isVisible() = visibility == View.VISIBLE

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.enabled() {
    this.isEnabled = true
}

fun View.disabled() {
    this.isEnabled = false
}

fun Context.showToast(message: String) {
    if (message.isNotEmpty())
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun <T> Context.startActivityClearTask(clazz: Class<T>) {
    startActivity(Intent(this, clazz).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    })
}

fun <T> Activity.startNewActivity(clazz: Class<T>) {
    startActivity(Intent(this, clazz))
}

fun <T> Activity.startActivityBundle(clazz: Class<T>, bundle: Bundle) {

    startActivity(
        Intent(this, clazz).apply { putExtras(bundle) }
    )
}

fun ImageView.load(imageUrl: String) {
    Glide.with(this).load(imageUrl).thumbnail(0.1f)
        .into(this)
}

fun <T> Fragment.startActivityBundleFromFragment(clazz: Class<T>, bundle: Bundle) {

    startActivity(
        Intent(requireActivity(), clazz).apply { putExtras(bundle) }
    )
}
