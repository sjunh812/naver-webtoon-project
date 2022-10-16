package org.sjhstudio.naverwebtoon.util

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS

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