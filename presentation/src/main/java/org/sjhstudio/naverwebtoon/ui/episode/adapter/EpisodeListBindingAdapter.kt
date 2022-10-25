package org.sjhstudio.naverwebtoon.ui.episode.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter

@BindingAdapter("webtoonInfoBackgroundColor")
fun ConstraintLayout.bindWebtoonInfoBackgroundColor(code: String?) {
    code?.let { setBackgroundColor(Color.parseColor(it)) }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("webtoonScore")
fun TextView.bindWebtoonScore(score: String?) {
    score?.takeIf { it.isNotEmpty() }?.let { text = "★ $it" }
}