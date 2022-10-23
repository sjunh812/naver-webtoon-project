package org.sjhstudio.naverwebtoon.ui.weekday.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.naverwebtoon.databinding.ItemWeekdayWebtoonBinding
import org.sjhstudio.naverwebtoon.domain.model.WebToon

class WeekdayAdapter(
    private val onClicked: (id: Long) -> Unit
) : ListAdapter<WebToon, WeekdayAdapter.DayListViewHolder>(diffCallback) {

    inner class DayListViewHolder(private val binding: ItemWeekdayWebtoonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                root.setOnClickListener {
                    val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return@setOnClickListener
                    val item = getItem(position)
                    item?.let { onClicked(it.id) }
                }
            }
        }

        fun bind(data: WebToon) {
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
        private val diffCallback = object : DiffUtil.ItemCallback<WebToon>() {
            override fun areItemsTheSame(oldItem: WebToon, newItem: WebToon): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: WebToon,
                newItem: WebToon
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
