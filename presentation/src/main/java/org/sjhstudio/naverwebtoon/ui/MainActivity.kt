package org.sjhstudio.naverwebtoon.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import org.sjhstudio.naverwebtoon.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}