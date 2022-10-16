package org.sjhstudio.naverwebtoon.ui.weekday.view

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.sjhstudio.naverwebtoon.R
import org.sjhstudio.naverwebtoon.base.BaseFragment
import org.sjhstudio.naverwebtoon.databinding.FragmentWeekdayListBinding
import org.sjhstudio.naverwebtoon.ui.weekday.adapter.*
import org.sjhstudio.naverwebtoon.ui.weekday.viewmodel.WeekdayListViewModel
import org.sjhstudio.naverwebtoon.util.setStatusBarMode

@AndroidEntryPoint
class WeekdayListFragment :
    BaseFragment<FragmentWeekdayListBinding>(R.layout.fragment_weekday_list) {

    private val weekdayListViewModel: WeekdayListViewModel by viewModels()
    private val weekdayPagerAdapter: WeekdayPagerAdapter by lazy { WeekdayPagerAdapter(this) }
    private val newWebToonAdapter: NewWebToonAdapter by lazy { NewWebToonAdapter() }

    private val toolbarInAnim: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.toolbar_in
        )
    }
    private val toolbarOutAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.toolbar_out).apply {
            setAnimationListener(ToolbarAnimationListener())
        }
    }
    private val frontThumbnailAnim: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.front_thumbnail
        )
    }
    private val backThumbnailAnim: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.back_thumbnail
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.appBar) { _, insets ->
            // Instead of
            // toolbar.setPadding(0, insets.systemWindowInsetTop, 0, 0)
            println("xxx SystemBar insects: ${insets.getInsets(WindowInsetsCompat.Type.systemBars())}")
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
            TabLayoutMediator(layoutTab, viewPagerWeekdayList) { tab, position ->
                tab.text = getTabTitle(position)
            }.attach()
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
            viewPagerWeekdayList.adapter = weekdayPagerAdapter
            viewPagerTopBanner.apply {
                adapter = newWebToonAdapter
                offscreenPageLimit = 1
                setPageTransformer { page, position ->
                    val title = page.findViewById<TextView>(R.id.tv_title).text
                    val frontThumbnail = page.findViewById<ImageView>(R.id.iv_front_thumbnail)
                    val backThumbnail = page.findViewById<ImageView>(R.id.iv_back_thumbnail)
                    if (position <= 0.5 && position > 0) {
                        if (!frontThumbnail.isVisible && !backThumbnail.isVisible) {
                            frontThumbnail.startAnimation(frontThumbnailAnim)
                            backThumbnail.startAnimation(backThumbnailAnim)
                        }
                        backThumbnail.isVisible = true
                        frontThumbnail.isVisible = true
                    } else if (position > 0.5 && position < 1 || title.isEmpty()) {
                        backThumbnail.isVisible = false
                        frontThumbnail.isVisible = false
                    } else {
                        backThumbnail.isVisible = true
                        frontThumbnail.isVisible = true
                    }

                    println("xxx Page transformer: $title - $position")
                }
            }
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
                    view?.pageDown(true)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(500)
                        view?.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('body')[0].innerHTML);")
                    }
                }
            }
        }
        binding.webView.loadUrl("https://m.comic.naver.com/index")
    }

    private fun observeData() {
        with(weekdayListViewModel) {
            lifecycleScope.launchWhenStarted {
                newList.collectLatest { list ->
                    list.takeIf { it.isNotEmpty() }?.let { newWebToons ->
                        newWebToonAdapter.submitList(newWebToons)
                    }
                }
            }
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
        viewModel.getNewWebToonList(html)
    }
}