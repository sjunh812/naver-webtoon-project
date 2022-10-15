package org.sjhstudio.naverwebtoon.ui.weekday.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.sjhstudio.naverwebtoon.ui.weekday.view.WeekdayFragment
import org.sjhstudio.naverwebtoon.ui.weekday.viewmodel.WeekdayListViewModel

const val WEEKDAY_INDEX = "weekday_index"
const val MONDAY_INDEX = 0
const val TUESDAY_INDEX = 1
const val WEDNESDAY_INDEX = 2
const val THURSDAY_INDEX = 3
const val FRIDAY_INDEX = 4
const val SATURDAY_INDEX = 5
const val SUNDAY_INDEX = 6

class WeekdayPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val tabFragmentsCreator: Map<Int, () -> Fragment> = mapOf(
        MONDAY_INDEX to {
            WeekdayFragment().apply {
                arguments = Bundle().apply { putInt(WEEKDAY_INDEX, MONDAY_INDEX) }
            }
        },
        TUESDAY_INDEX to {
            WeekdayFragment().apply {
                arguments = Bundle().apply { putInt(WEEKDAY_INDEX, TUESDAY_INDEX) }
            }
        },
        WEDNESDAY_INDEX to {
            WeekdayFragment().apply {
                arguments = Bundle().apply { putInt(WEEKDAY_INDEX, WEDNESDAY_INDEX) }
            }
        },
        THURSDAY_INDEX to {
            WeekdayFragment().apply {
                arguments = Bundle().apply { putInt(WEEKDAY_INDEX, THURSDAY_INDEX) }
            }
        },
        FRIDAY_INDEX to {
            WeekdayFragment().apply {
                arguments = Bundle().apply { putInt(WEEKDAY_INDEX, FRIDAY_INDEX) }
            }
        },
        SATURDAY_INDEX to {
            WeekdayFragment().apply {
                arguments = Bundle().apply { putInt(WEEKDAY_INDEX, SATURDAY_INDEX) }
            }
        },
        SUNDAY_INDEX to {
            WeekdayFragment().apply {
                arguments = Bundle().apply { putInt(WEEKDAY_INDEX, SUNDAY_INDEX) }
            }
        }
    )

    override fun getItemCount(): Int = tabFragmentsCreator.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}