package org.sjhstudio.naverwebtoon.util

import android.annotation.SuppressLint
import android.content.Context
import kotlin.math.roundToInt

fun Context.dpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).roundToInt()

fun Context.pxToDp(px: Float): Int = (px / resources.displayMetrics.density).toInt()

@SuppressLint("InternalInsetResource")
fun Context.getStatusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelOffset(resourceId) else 0
}