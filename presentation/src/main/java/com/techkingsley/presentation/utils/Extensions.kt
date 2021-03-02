package com.techkingsley.presentation.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.from(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return simpleDateFormat.format(this)
}

