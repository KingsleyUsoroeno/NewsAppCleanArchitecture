package com.techkingsley.newsappcleanarchitecture.framework.presentation.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.techkingsley.newsappcleanarchitecture.R

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url ?: "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/casino-royale-james-bond-daniel-craig-1548342795.jpg?crop=0.564xw:1.00xh;0.332xw,0&resize=480:*")
        .placeholder(R.drawable.progress_animation)
        .error(R.drawable.ic_broken_image)
        .into(imageView)
}