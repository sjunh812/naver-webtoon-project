package org.sjhstudio.naverwebtoon.ui.episode.view

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.graphics.alpha
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.sjhstudio.naverwebtoon.R
import org.sjhstudio.naverwebtoon.base.BaseActivity
import org.sjhstudio.naverwebtoon.databinding.ActivityEpisodeListBinding
import org.sjhstudio.naverwebtoon.ui.episode.viewmodel.EpisodeListViewModel
import org.sjhstudio.naverwebtoon.util.setStatusBarMode
import org.sjhstudio.naverwebtoon.util.showConfirmAlertDialog
import kotlin.math.abs

@AndroidEntryPoint
class EpisodeListActivity :
    BaseActivity<ActivityEpisodeListBinding>(R.layout.activity_episode_list) {

    private val episodeListViewModel: EpisodeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.appBar) { _, insets ->
            binding.toolbar.setPadding(
                0,
                insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                0,
                0
            )
            WindowInsetsCompat.CONSUMED
        }
        bind()
        initView()
        observeData()
    }

    private fun bind() {
        with(binding) {
            viewModel = episodeListViewModel
        }
    }

    private fun initView() {
        with(binding) {
            appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                val totalScrollRange = appBarLayout.totalScrollRange
                val percentage = 1f - abs(verticalOffset).toFloat() / totalScrollRange.toFloat()
                if (percentage < 1.0f) {
                    setStatusBarMode(window, true)
                } else {
                    setStatusBarMode(window, false)
                }
                if (percentage >= 0.6f) {
                    val color = Color.argb(((1 - percentage)/ 0.4f * 255).toInt(), 255, 255, 255)
                    toolbar.setBackgroundColor(color)
                    if (tvToolbarTitle.text.isNotEmpty()) tvToolbarTitle.text = ""
                } else {
                    val color = Color.argb(255, 255, 255, 255)
                    toolbar.setBackgroundColor(color)
                    if (tvToolbarTitle.text.isEmpty()) tvToolbarTitle.text = tvTitle.text
//                    toolbar.title = episodeListViewModel.webToonInfo.value?.title
                }
                Log.e("debug", "percentage: $percentage")
            }
            scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                Log.e("debug", "scrollX: $scrollX, scrollY: $scrollY")
            }
        }
    }

    private fun observeData() {
        with(episodeListViewModel) {
            lifecycleScope.launchWhenStarted {
                webtoonInfo.collectLatest { webToonInfo ->
                    webToonInfo?.let { info ->
                        Log.e("debug", "$info")
                        if (info.adult) showConfirmAlertDialog("해당 웹툰은 성인 웹툰으로\n이용이 제한됩니다.") {
                            finish()
                        }
                    }
                }
            }
        }
    }

    companion object {

        const val WEEKDAY = "weekday"
        const val TITLE_ID = "title_id"
    }
}