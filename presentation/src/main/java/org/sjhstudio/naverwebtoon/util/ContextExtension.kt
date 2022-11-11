package org.sjhstudio.naverwebtoon.util

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.math.roundToInt

fun Context.dpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).roundToInt()

fun Context.pxToDp(px: Float): Int = (px / resources.displayMetrics.density).toInt()

@SuppressLint("InternalInsetResource")
fun Context.getStatusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelOffset(resourceId) else 0
}

fun Context.showConfirmAlertDialog(message: String, onConfirmed: () -> Unit) {
    MaterialAlertDialogBuilder(this)
        .setCancelable(false)
        .setMessage(message)
        .setPositiveButton("확인") { _, _ -> onConfirmed() }
        .create()
        .show()
}