package org.sjhstudio.naverwebtoon.ui.weekday.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget

@BindingAdapter("thumbnailImageUrl")
fun ImageView.bindThumbnailImageUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
            .into(this)
    }
}

@BindingAdapter("showUpdateBadge")
fun View.bindShowUpdateBadge(update: Boolean) {
    isVisible = update
}

@SuppressLint("SetTextI18n")
@BindingAdapter("rating")
fun TextView.bindRating(rating: String?) {
    if (!rating.isNullOrEmpty()) {
        text = "★ $rating"
    }
}