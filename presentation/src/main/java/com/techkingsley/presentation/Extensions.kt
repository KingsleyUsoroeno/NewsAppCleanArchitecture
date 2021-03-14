package com.techkingsley.presentation

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

val Date.from: String
    get() {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return simpleDateFormat.format(this)
    }

/**
 * Remove the view (visibility = View.GONE)
 */

val View.hide: View
    get() {
        if (visibility != View.GONE) {
            visibility = View.GONE
        }
        return this
    }

/**
 * Show the view  (visibility = View.VISIBLE)
 */
val View.show: View
    get() {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
        return this
    }

