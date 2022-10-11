package org.sjhstudio.naverwebtoon.ui.weekday.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.naverwebtoon.databinding.ItemWeekdayWebtoonBinding
import org.sjhstudio.naverwebtoon.domain.model.MobileWebToon

class WeekdayAdapter :
    ListAdapter<MobileWebToon, WeekdayAdapter.DayListViewHolder>(diffCallback) {

    class DayListViewHolder(private val binding: ItemWeekdayWebtoonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MobileWebToon) {
            with(binding) {
                webToon = data
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayListViewHolder {
        val binding =
            ItemWeekdayWebtoonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayListViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<MobileWebToon>() {
            override fun areItemsTheSame(oldItem: MobileWebToon, newItem: MobileWebToon): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MobileWebToon,
                newItem: MobileWebToon
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
