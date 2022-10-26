package org.sjhstudio.naverwebtoon.ui.episode.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.sjhstudio.naverwebtoon.R
import org.sjhstudio.naverwebtoon.base.BaseActivity
import org.sjhstudio.naverwebtoon.databinding.ActivityEpisodeListBinding
import org.sjhstudio.naverwebtoon.ui.episode.viewmodel.EpisodeListViewModel
import org.sjhstudio.naverwebtoon.util.pxToDp
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
            appBar.addOnOffsetChangedListener { appBar, verticalOffset ->
                val standard = 1 - SHOW_TOOLBAR_OFFSET / pxToDp(appBar.height.toFloat()).toFloat()
                val percentage = 1f - abs(verticalOffset).toFloat() / appBar.totalScrollRange
                Log.e("debug", "AppBar offset percent: $percentage")
                setStatusBarMode(window = window, isLightMode = percentage < 1.0f)

                if (percentage >= standard) {
                    val color = Color.argb(((1 - percentage) / 0.4f * 255).toInt(), 255, 255, 255)
                    toolbar.setBackgroundColor(color)
                    if (tvToolbarTitle.text.isNotEmpty()) tvToolbarTitle.text = ""
                } else {
                    val color = Color.argb(255, 255, 255, 255)
                    toolbar.setBackgroundColor(color)
                    if (tvToolbarTitle.text.isEmpty()) tvToolbarTitle.text = tvTitle.text
                }
            }
            layoutWebtoonInfoDetail.setOnClickListener {
                handleWebtoonDetailView()
            }
        }
    }

    private fun observeData() {
        with(episodeListViewModel) {
            lifecycleScope.launchWhenStarted {
                webtoonInfo.collectLatest { webtoonInfo ->
                    webtoonInfo?.let { info ->
                        if (info.isAdult) {
                            showConfirmAlertDialog("해당 웹툰은 성인 웹툰으로\n이용이 제한됩니다.") {
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleWebtoonDetailView() {
        if (episodeListViewModel.detailExpanded) {
            episodeListViewModel.detailExpanded = false
            binding.layoutWebtoonInfoDetailHide.isVisible = false
            binding.tvSummaryDetail.maxLines = 1
            binding.ivArrow.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_down_arrow
                )
            )
        } else {
            episodeListViewModel.detailExpanded = true
            binding.layoutWebtoonInfoDetailHide.isVisible = true
            binding.tvSummaryDetail.maxLines = Int.MAX_VALUE
            binding.ivArrow.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_up_arrow
                )
            )
        }
    }

    companion object {

        const val WEEKDAY = "weekday"
        const val TITLE_ID = "title_id"

        private const val SHOW_TOOLBAR_OFFSET = 85
    }
}