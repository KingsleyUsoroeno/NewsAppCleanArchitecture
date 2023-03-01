package com.techkingsley.newsappcleanarchitecture.utils.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.techkingsley.newsappcleanarchitecture.R
import java.util.*


@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {

    val imageUrl: String = if (url.isNullOrEmpty()) {
        "https://i.insider.com/5de7b7e779d75748a44f7012?width=900"
    } else {
        url
    }

    Glide.with(imageView.context)
        .load(imageUrl)
        .placeholder(R.drawable.progress_animation)
        .error(R.drawable.ic_broken_image)
        .into(imageView)
}

@BindingAdapter("setImageDrawable")
fun setImageDrawable(imageView: AppCompatImageView, resource: Drawable) {
    imageView.setImageDrawable(resource)
}