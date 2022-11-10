package org.sjhstudio.naverwebtoon.ui.episode.view

import android.content.Intent
import android.os.Bundle
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
import org.sjhstudio.naverwebtoon.ui.episode.adapter.EpisodeAdapter
import org.sjhstudio.naverwebtoon.ui.episode.viewmodel.EpisodeListViewModel
import org.sjhstudio.naverwebtoon.ui.viewer.view.ViewerActivity
import org.sjhstudio.naverwebtoon.util.pxToDp
import org.sjhstudio.naverwebtoon.util.setStatusBarMode
import org.sjhstudio.naverwebtoon.util.showConfirmAlertDialog
import kotlin.math.abs

@AndroidEntryPoint
class EpisodeListActivity :
    BaseActivity<ActivityEpisodeListBinding>(R.layout.activity_episode_list) {

    private val episodeListViewModel: EpisodeListViewModel by viewModels()
    private val episodeAdapter: EpisodeAdapter by lazy {
        EpisodeAdapter { episode ->
            val intent = Intent(this, ViewerActivity::class.java).apply {
                putExtra(TITLE_ID, episode.titleId)
                putExtra(TITLE, episode.title)
                putExtra(DATA_NO, episode.dataNo)
            }
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.appBar) { _, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            binding.toolbar.setPadding(0, statusBarHeight, 0, 0)
            WindowInsetsCompat.CONSUMED
        }
        bind()
        initView()
        observeData()
    }

    private fun bind() {
        with(binding) {
            viewModel = episodeListViewModel
            adapter = episodeAdapter
        }
    }

    private fun initView() {
        with(binding) {
            appBar.addOnOffsetChangedListener { appBar, verticalOffset ->
                val standard = 1f - SHOW_TOOLBAR_OFFSET / pxToDp(appBar.height.toFloat())
                val offset = 1f - abs(verticalOffset).toFloat() / appBar.totalScrollRange
                val toolbarBackground =
                    ContextCompat.getDrawable(this@EpisodeListActivity, R.drawable.bg_toolbar)

                setStatusBarMode(window = window, isLightMode = offset < 1.0f)

                if (offset >= standard) {
                    toolbarBackground?.alpha = ((1 - offset) / 0.4f * 255).toInt()

                    if (tvToolbarTitle.text.isNotEmpty()) {
                        tvToolbarTitle.text = ""
                    }
                } else {
                    if (tvToolbarTitle.text.isEmpty()) {
                        tvToolbarTitle.text = tvTitle.text
                    }
                }

                toolbar.background = toolbarBackground
            }
            ivBack.setOnClickListener {
                onBackPressed()
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
                            showConfirmAlertDialog(
                                message = "해당 웹툰은 성인 웹툰으로\n이용이 제한됩니다."
                            ) {
                                finish()
                            }
                        }
                    }
                }
            }
            lifecycleScope.launchWhenStarted {
                episodePagingData.collectLatest { pagingData ->
                    pagingData?.let { data ->
                        episodeAdapter.submitData(data)
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
        private const val SHOW_TOOLBAR_OFFSET = 85f

        const val TITLE_ID = "title_id"
        const val TITLE = "title"
        const val DATA_NO = "data_no"
    }
}