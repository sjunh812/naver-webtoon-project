package org.sjhstudio.naverwebtoon.ui.weekday.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import org.sjhstudio.naverwebtoon.R
import org.sjhstudio.naverwebtoon.base.BaseFragment
import org.sjhstudio.naverwebtoon.databinding.FragmentWeekdayBinding
import org.sjhstudio.naverwebtoon.domain.model.Weekday
import org.sjhstudio.naverwebtoon.ui.episode.view.EpisodeListActivity
import org.sjhstudio.naverwebtoon.ui.weekday.adapter.*
import org.sjhstudio.naverwebtoon.ui.weekday.viewmodel.WeekdayListViewModel

class WeekdayFragment : BaseFragment<FragmentWeekdayBinding>(R.layout.fragment_weekday) {

    private val viewModel: WeekdayListViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private val dayListAdapter: WeekdayAdapter by lazy {
        WeekdayAdapter { id ->
            val intent = Intent(requireContext(), EpisodeListActivity::class.java).apply {
                putExtra(WEEKDAY, getWeek())
                putExtra(TITLE_ID, id)
            }
            startActivity(intent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
        observeData()
    }

    private fun bind() {
        with(binding) {
            adapter = dayListAdapter
        }
    }

    private fun observeData() {
        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                weekdayList.collectLatest { map ->
                    map[getWeek()]?.let { list -> dayListAdapter.submitList(list) }
                }
            }
        }
    }

    private fun getWeek(): String? = when (arguments?.getInt(WEEKDAY_INDEX)) {
        MONDAY_INDEX -> Weekday.MON.english
        TUESDAY_INDEX -> Weekday.TUE.english
        WEDNESDAY_INDEX -> Weekday.WED.english
        THURSDAY_INDEX -> Weekday.THU.english
        FRIDAY_INDEX -> Weekday.FRI.english
        SATURDAY_INDEX -> Weekday.SAT.english
        SUNDAY_INDEX -> Weekday.SUN.english
        else -> null
    }

    companion object {
        const val WEEKDAY = "weekday"
        const val TITLE_ID = "title_id"
    }
}