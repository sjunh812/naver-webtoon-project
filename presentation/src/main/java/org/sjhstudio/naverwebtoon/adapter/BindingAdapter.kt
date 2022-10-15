package org.sjhstudio.naverwebtoon.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@BindingAdapter("imageFromUrl")
fun ImageView.bindImageFromUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        visibility = View.VISIBLE
        Glide.with(context)
            .load(url)
            .into(this)
    } else visibility = View.GONE
}

@BindingAdapter("adapter")
fun RecyclerView.bindAdapter(adapter: RecyclerView.Adapter<*>) {
    this.adapter = adapter
}