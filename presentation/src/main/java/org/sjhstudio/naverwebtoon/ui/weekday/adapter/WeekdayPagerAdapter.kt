package org.sjhstudio.naverwebtoon.ui.weekday.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.sjhstudio.naverwebtoon.ui.weekday.view.DayListFragment
import org.sjhstudio.naverwebtoon.ui.weekday.viewmodel.WeekdayListViewModel

const val MONDAY_INDEX = 0
const val TUESDAY_INDEX = 1
const val WEDNESDAY_INDEX = 2
const val THURSDAY_INDEX = 3
const val FRIDAY_INDEX = 4
const val SATURDAY_INDEX = 5
const val SUNDAY_INDEX = 6

class WeekdayPagerAdapter(fragment: Fragment, viewModel: WeekdayListViewModel) :
    FragmentStateAdapter(fragment) {

    private val tabFragmentsCreator: Map<Int, () -> Fragment> = mapOf(
        MONDAY_INDEX to { DayListFragment(MONDAY_INDEX, viewModel) },
        TUESDAY_INDEX to { DayListFragment(TUESDAY_INDEX, viewModel) },
        WEDNESDAY_INDEX to { DayListFragment(WEDNESDAY_INDEX, viewModel) },
        THURSDAY_INDEX to { DayListFragment(THURSDAY_INDEX, viewModel) },
        FRIDAY_INDEX to { DayListFragment(FRIDAY_INDEX, viewModel) },
        SATURDAY_INDEX to { DayListFragment(SATURDAY_INDEX, viewModel) },
        SUNDAY_INDEX to { DayListFragment(SUNDAY_INDEX, viewModel) }
    )

    override fun getItemCount(): Int = tabFragmentsCreator.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}