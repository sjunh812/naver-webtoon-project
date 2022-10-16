package org.sjhstudio.naverwebtoon.adapter

import android.graphics.drawable.Drawable
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.sjhstudio.naverwebtoon.R

@BindingAdapter("imageFromUrl")
fun ImageView.bindImageFromUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(context)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (url.contains("backImage") && !isVisible) {
                        isVisible = true
                        startAnimation(AnimationUtils.loadAnimation(context, R.anim.back_thumbnail))
                    } else if (url.contains("frontImage") && !isVisible) {
                        isVisible = true
                        startAnimation(AnimationUtils.loadAnimation(context, R.anim.front_thumbnail))
                    }
                    return false
                }
            })
            .into(this)
    } else setImageResource(0)
}

@BindingAdapter("adapter")
fun RecyclerView.bindAdapter(adapter: RecyclerView.Adapter<*>) {
    this.adapter = adapter
}