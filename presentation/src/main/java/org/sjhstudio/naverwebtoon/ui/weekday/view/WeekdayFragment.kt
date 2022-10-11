package org.sjhstudio.naverwebtoon.ui.weekday.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import org.sjhstudio.naverwebtoon.R
import org.sjhstudio.naverwebtoon.base.BaseFragment
import org.sjhstudio.naverwebtoon.databinding.FragmentDayListBinding
import org.sjhstudio.naverwebtoon.ui.weekday.adapter.*
import org.sjhstudio.naverwebtoon.ui.weekday.viewmodel.WeekdayListViewModel

class WeekdayFragment(private val position: Int, private val viewModel: WeekdayListViewModel) :
    BaseFragment<FragmentDayListBinding>(R.layout.fragment_day_list) {

    private val dayListAdapter: WeekdayAdapter by lazy { WeekdayAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getDayList()
    }

    private fun initView() {
        with(binding) {
            rvDayList.adapter = dayListAdapter

        }
    }

    private fun getDayList() {
        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                weekdayList.collectLatest { map ->
                    map[getWeek()]?.let { list -> dayListAdapter.submitList(list) }
                }
            }
        }
    }

    private fun getWeek(): String? = when(position) {
        MONDAY_INDEX -> "mon"
        TUESDAY_INDEX -> "tue"
        WEDNESDAY_INDEX -> "wed"
        THURSDAY_INDEX -> "thu"
        FRIDAY_INDEX -> "fri"
        SATURDAY_INDEX -> "sat"
        SUNDAY_INDEX -> "sun"
        else -> null
    }
}