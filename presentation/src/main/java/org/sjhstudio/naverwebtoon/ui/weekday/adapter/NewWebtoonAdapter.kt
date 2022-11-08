package org.sjhstudio.naverwebtoon.ui.weekday.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.naverwebtoon.databinding.ItemNewWebtoonBinding
import org.sjhstudio.naverwebtoon.domain.model.NewWebtoon
import org.sjhstudio.naverwebtoon.util.getStatusBarHeight

class NewWebtoonAdapter :
    ListAdapter<NewWebtoon, NewWebtoonAdapter.NewWebtoonViewHolder>(diffCallback) {

    class NewWebtoonViewHolder(private val binding: ItemNewWebtoonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                tvTitle.setPadding(0, tvTitle.context.getStatusBarHeight(), 0, 0)
            }
        }

        fun bind(data: NewWebtoon) {
            with(binding) {
                newWebtoon = data
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewWebtoonViewHolder {
        val binding =
            ItemNewWebtoonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewWebtoonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewWebtoonViewHolder, position: Int) {
        val item = getItem(position % currentList.size)
        item?.let { holder.bind(it) }
    }

    override fun getItemCount() = Int.MAX_VALUE

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<NewWebtoon>() {
            override fun areItemsTheSame(oldItem: NewWebtoon, newItem: NewWebtoon) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: NewWebtoon, newItem: NewWebtoon) =
                oldItem == newItem
        }
    }
}