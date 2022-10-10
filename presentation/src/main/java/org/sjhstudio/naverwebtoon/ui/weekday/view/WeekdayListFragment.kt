package org.sjhstudio.naverwebtoon.ui.weekday.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            viewPagerWeekdayList.adapter = weekdayPagerAdapter
            TabLayoutMediator(layoutTab, viewPagerWeekdayList) { tab, position ->
                tab.text = getTabTitle(position)
            }.attach()
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