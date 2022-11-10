package org.sjhstudio.naverwebtoon.ui.viewer.view

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.sjhstudio.naverwebtoon.R
import org.sjhstudio.naverwebtoon.base.BaseActivity
import org.sjhstudio.naverwebtoon.databinding.ActivityViewerBinding
import org.sjhstudio.naverwebtoon.ui.viewer.adapter.ViewerAdapter
import org.sjhstudio.naverwebtoon.ui.viewer.viewmodel.ViewerViewModel

@AndroidEntryPoint
class ViewerActivity : BaseActivity<ActivityViewerBinding>(R.layout.activity_viewer) {

    private val viewerViewModel: ViewerViewModel by viewModels()
    private val viewerAdapter: ViewerAdapter by lazy { ViewerAdapter() }
    private val toolbarInAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.toolbar_in)
    }
    private val toolbarOutAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.toolbar_out).apply {
            setAnimationListener(ToolbarAnimationListener())
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
            adapter = viewerAdapter
        }
    }

    private fun initView() {
        with(binding) {
            tvToolbarTitle.text = viewerViewModel.title

            rvViewer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (recyclerView.canScrollVertically(-1) && recyclerView.canScrollVertically(1)) {
                        hideBars()
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(-1) || !recyclerView.canScrollVertically(1)) {
                        showBars()
                    } else {
                        hideBars()
                    }
                }
            })
        }
    }

    private fun observeData() {
        with(viewerViewModel) {
            lifecycleScope.launchWhenStarted {
                viewerList.collectLatest { pagingData ->
                    viewerAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun hideBars() {
        if (viewerViewModel.showBars) {
            viewerViewModel.showBars = false
            binding.toolbar.startAnimation(toolbarOutAnim)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                WindowInsetsControllerCompat(
                    window,
                    window.decorView
                ).hide(WindowInsetsCompat.Type.statusBars())
            } else {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
        }
    }

    private fun showBars() {
        if (!viewerViewModel.showBars) {
            viewerViewModel.showBars = true
            binding.toolbar.isVisible = true
            binding.toolbar.startAnimation(toolbarInAnim)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                WindowInsetsControllerCompat(
                    window,
                    window.decorView
                ).show(WindowInsetsCompat.Type.statusBars())
            } else {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
                )
            }
        }
    }

    inner class ToolbarAnimationListener : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {
            binding.toolbar.isVisible = false
        }

        override fun onAnimationRepeat(p0: Animation?) {}
    }
}