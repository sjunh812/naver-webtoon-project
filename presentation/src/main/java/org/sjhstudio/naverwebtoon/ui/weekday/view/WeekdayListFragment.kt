package org.sjhstudio.naverwebtoon.ui.weekday.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.joda.time.DateTime
import org.sjhstudio.naverwebtoon.R
import org.sjhstudio.naverwebtoon.base.BaseFragment
import org.sjhstudio.naverwebtoon.databinding.FragmentWeekdayListBinding
import org.sjhstudio.naverwebtoon.domain.model.NewWebtoon
import org.sjhstudio.naverwebtoon.ui.weekday.adapter.*
import org.sjhstudio.naverwebtoon.ui.weekday.viewmodel.WeekdayListViewModel
import org.sjhstudio.naverwebtoon.util.setCurrentItemWithDuration
import org.sjhstudio.naverwebtoon.util.setStatusBarMode

@AndroidEntryPoint
class WeekdayListFragment :
    BaseFragment<FragmentWeekdayListBinding>(R.layout.fragment_weekday_list) {

    private val weekdayListViewModel: WeekdayListViewModel by viewModels()
    private val weekdayPagerAdapter: WeekdayPagerAdapter by lazy { WeekdayPagerAdapter(this) }
    private val newWebToonAdapter: NewWebToonAdapter by lazy { NewWebToonAdapter() }

    private val toolbarInAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.toolbar_in)
    }
    private val toolbarOutAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.toolbar_out).apply {
            setAnimationListener(ToolbarAnimationListener())
        }
    }
    private val frontThumbnailAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.front_thumbnail)
    }
    private val backThumbnailAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.back_thumbnail)
    }

    private var topBannerScrollJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.appBar) { _, insets ->
            binding.toolbar.setPadding(
                0,
                insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                0,
                0
            )
            WindowInsetsCompat.CONSUMED
        }
        initView()
        initWebView()
        observeData()
    }

    private fun initView() {
        with(binding) {
            appBar.addOnOffsetChangedListener { _, verticalOffset ->
                if (verticalOffset != 0 && !toolbar.isVisible) {
                    toolbar.isVisible = true
                    toolbar.startAnimation(toolbarInAnim)
                    setStatusBarMode(
                        window = requireActivity().window,
                        isLightMode = true
                    )
                } else if (verticalOffset == 0 && toolbar.isVisible) {
                    toolbar.startAnimation(toolbarOutAnim)
                }
            }
            viewPagerWeekdayList.apply {
                adapter = weekdayPagerAdapter
                setCurrentItem(DateTime().dayOfWeek - 1, false)
            }
            viewPagerWeekdayList.adapter = weekdayPagerAdapter
            TabLayoutMediator(layoutTab, viewPagerWeekdayList) { tab, position ->
                tab.text = getTabTitle(position)
            }.attach()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            setInitialScale(1)
            addJavascriptInterface(WebToonJavascriptInterface(weekdayListViewModel), "Android")
            webViewClient = object : WebViewClient() {

                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)
                    Log.e("debug", "onPageCommitVisible()")
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Log.e("debug", "onPageFinished()")
                    lifecycleScope.launchWhenStarted {
                        delay(1000)
                        binding.webView.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('body')[0].innerHTML);")
                    }
                }
            }
            loadUrl("https://m.comic.naver.com/")
        }
    }

    private fun initTopBannerViewPager(list: List<NewWebtoon>) {
        with(binding.viewPagerTopBanner) {
            adapter = newWebToonAdapter
            setCurrentItem(
                Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % list.size),
                false
            )
            setPageTransformer { page, position ->
                val title = page.findViewById<TextView>(R.id.tv_title).text
                val frontThumbnail = page.findViewById<ImageView>(R.id.iv_front_thumbnail)
                val backThumbnail = page.findViewById<ImageView>(R.id.iv_back_thumbnail)

                if (position > 0 && position <= 0.5) {
                    if (!frontThumbnail.isVisible && !backThumbnail.isVisible) {
                        println("xxx Animation start: $position - $title")
                        handleThumbnailAnimation(frontThumbnail, backThumbnail, true)
                        handleThumbnailVisible(frontThumbnail, backThumbnail, true)
                    } else {
                        println("xxx Animation already started: $position - $title")
                    }
                } else if (position > 0.5) {
                    println("xxx Animation start yet: $position - $title")
                    handleThumbnailVisible(frontThumbnail, backThumbnail, false)
                } else if (position < 0) {
                    println("xxx Animation clear: $position - $title")
                    handleThumbnailVisible(frontThumbnail, backThumbnail, true)
                    handleThumbnailAnimation(frontThumbnail, backThumbnail, false)
                } else {
                    if (title.isEmpty()) {
                        println("xxx Init: $position - $title")
                        handleThumbnailAnimation(frontThumbnail, backThumbnail, true)
                        createTopBannerScrollJob()
                    }
                }
            }
            registerOnPageChangeCallback(object : OnPageChangeCallback() {

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        ViewPager2.SCROLL_STATE_IDLE -> createTopBannerScrollJob()
                        ViewPager2.SCROLL_STATE_DRAGGING -> {}
                        ViewPager2.SCROLL_STATE_SETTLING -> {}
                    }
                }
            })
        }
    }

    private fun observeData() {
        with(weekdayListViewModel) {
            lifecycleScope.launchWhenStarted {
                newList.collectLatest { list ->
                    list.takeIf { it.isNotEmpty() }?.let { newWebToons ->
                        newWebToonAdapter.submitList(newWebToons)
                        initTopBannerViewPager(newWebToons)
                    }
                }
            }
        }
    }

    private fun handleThumbnailVisible(frontThumb: View, backThumb: View, visible: Boolean) {
        frontThumb.isVisible = visible
        backThumb.isVisible = visible
    }

    private fun handleThumbnailAnimation(frontThumb: View, backThumb: View, start: Boolean) {
        if (start) {
            frontThumb.startAnimation(frontThumbnailAnim)
            backThumb.startAnimation(backThumbnailAnim)
        } else {
            frontThumb.clearAnimation()
            backThumb.clearAnimation()
        }
    }

    fun createTopBannerScrollJob() {
        if (topBannerScrollJob != null) topBannerScrollJob?.cancel()
        topBannerScrollJob = lifecycleScope.launchWhenResumed {
            delay(3000)
            binding.viewPagerTopBanner.setCurrentItemWithDuration(
                binding.viewPagerTopBanner.currentItem + 1,
                500
            )
        }
    }

    private fun getTabTitle(position: Int): String? = when (position) {
        MONDAY_INDEX -> "월"
        TUESDAY_INDEX -> "화"
        WEDNESDAY_INDEX -> "수"
        THURSDAY_INDEX -> "목"
        FRIDAY_INDEX -> "금"
        SATURDAY_INDEX -> "토"
        SUNDAY_INDEX -> "일"
        else -> null
    }

    inner class ToolbarAnimationListener : Animation.AnimationListener {

        override fun onAnimationStart(p0: Animation?) {}

        override fun onAnimationEnd(p0: Animation?) {
            setStatusBarMode(requireActivity().window, false)
            binding.toolbar.isVisible = false
        }

        override fun onAnimationRepeat(p0: Animation?) {}
    }
}

private class WebToonJavascriptInterface(private val viewModel: WeekdayListViewModel) {

    @JavascriptInterface
    fun getHtml(html: String) {
        Log.e("debug", "html length: ${html.length}")
        viewModel.getNewWebToonList(html)
    }
}