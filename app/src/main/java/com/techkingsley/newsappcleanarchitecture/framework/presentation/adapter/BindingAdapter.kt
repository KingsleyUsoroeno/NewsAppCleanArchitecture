package com.techkingsley.newsappcleanarchitecture.framework.presentation.adapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}