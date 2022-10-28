package org.sjhstudio.naverwebtoon.ui.episode.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.sjhstudio.naverwebtoon.util.dpToPx

@BindingAdapter("webtoonInfoBackgroundColor")
fun ConstraintLayout.bindWebtoonInfoBackgroundColor(code: String?) {
    code?.let { setBackgroundColor(Color.parseColor(it)) }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("webtoonScore")
fun TextView.bindWebtoonScore(score: String?) {
    score?.takeIf { it.isNotEmpty() }?.let { text = "★ $it" }
}

@BindingAdapter("episodeImageFromUrl")
fun ImageView.bindEpisodeImageFromUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
            .override(context.dpToPx(101), context.dpToPx(51))
            .into(this)
    }
}