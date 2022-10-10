package org.sjhstudio.naverwebtoon.ui.weekday.adapter

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("thumbnailImageUrl")
fun ImageView.bindThumbnailImageUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(15)))
            .into(this)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("rating")
fun TextView.bindRating(rating: String?) {
    if (!rating.isNullOrEmpty()) {
        text = "★ $rating"
    }
}