package org.sjhstudio.naverwebtoon.ui.weekday.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
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

@AndroidEntryPoint
class WeekdayListFragment :
    BaseFragment<FragmentWeekdayListBinding>(R.layout.fragment_weekday_list) {

    private val weekdayListViewModel: WeekdayListViewModel by viewModels()
    private val weekdayPagerAdapter: WeekdayPagerAdapter by lazy {
        WeekdayPagerAdapter(
            this,
            weekdayListViewModel
        )
    }
    private val newWebToonAdapter: NewWebToonAdapter by lazy { NewWebToonAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initWebView()
        observeData()
    }

    private fun initView() {
        with(binding) {
            viewPagerWeekdayList.adapter = weekdayPagerAdapter
            viewPagerTopBanner.adapter = newWebToonAdapter
            TabLayoutMediator(layoutTab, viewPagerWeekdayList) { tab, position ->
                tab.text = getTabTitle(position)
            }.attach()


//            scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
////                println("xxx scrollY: $scrollY")
//            }
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
                        println("xxx $newWebToons")
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
}

private class WebToonJavascriptInterface(private val viewModel: WeekdayListViewModel) {

    @JavascriptInterface
    fun getHtml(html: String) {
        viewModel.getNewWebToonList(html)
    }
}