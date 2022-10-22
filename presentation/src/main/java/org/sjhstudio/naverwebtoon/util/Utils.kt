package org.sjhstudio.naverwebtoon.util

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.viewpager2.widget.ViewPager2

fun setStatusBarMode(window: Window, isLightMode: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.let { insetsController ->
            if (isLightMode) {
                insetsController.setSystemBarsAppearance(
                    APPEARANCE_LIGHT_STATUS_BARS,   // value
                    APPEARANCE_LIGHT_STATUS_BARS    // mask
                )
            } else {
                insetsController.setSystemBarsAppearance(
                    0,                              // value
                    APPEARANCE_LIGHT_STATUS_BARS    // mask
                )
            }
        }
    } else {
        val flag = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility =
            if (isLightMode) flag or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else flag and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun ViewPager2.setCurrentItemWithDuration(
    item: Int,
    duration: Long,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    pagePxWidth: Int = width // ViewPager2 View 의 getWidth()에서 가져온 기본값
) {
    val pxToDrag: Int = pagePxWidth * (item - currentItem)
    val animator = ValueAnimator.ofInt(0, pxToDrag)
    var previousValue = 0

    animator.addUpdateListener { valueAnimator ->
        val currentValue = valueAnimator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        fakeDragBy(-currentPxToDrag)
        previousValue = currentValue
    }

    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) { beginFakeDrag() }
        override fun onAnimationEnd(animation: Animator?) { endFakeDrag() }
        override fun onAnimationCancel(animation: Animator?) { /* Ignored */ }
        override fun onAnimationRepeat(animation: Animator?) { /* Ignored */ }
    })

    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}