package org.sjhstudio.naverwebtoon.ui.weekday.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.sjhstudio.naverwebtoon.util.dpToPx

@BindingAdapter("thumbnailImageUrl")
fun ImageView.bindThumbnailImageUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
            .override(context.dpToPx(138), context.dpToPx(180))
            .into(this)
    }
}

@BindingAdapter("showUpdateBadge")
fun View.bindShowUpdateBadge(update: Boolean) {
    isVisible = update
}

@BindingAdapter("newWebToonBackgroundColor")
fun ConstraintLayout.bindNewWebToonBackgroundColor(colorList: List<Int>) {
    setBackgroundColor(Color.rgb(colorList[0], colorList[1], colorList[2]))
}