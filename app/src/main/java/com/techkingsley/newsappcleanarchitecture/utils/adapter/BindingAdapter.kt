package com.techkingsley.newsappcleanarchitecture.utils.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.techkingsley.newsappcleanarchitecture.R
import java.util.*

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("showOrDismissProgressbar")
fun showOrDismissProgressbar(view: View, it: LiveData<Any?>) {
    view.visibility = if (it.value == null) View.VISIBLE else View.GONE
}

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

fun setChipBackgroundTint(view: View) {
    val rnd = Random()
    val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    view.background.setTint(color)
}

@BindingAdapter("setImageDrawable")
fun setImageDrawable(imageView: AppCompatImageView, resource: Drawable) {
    imageView.setImageDrawable(resource)
}