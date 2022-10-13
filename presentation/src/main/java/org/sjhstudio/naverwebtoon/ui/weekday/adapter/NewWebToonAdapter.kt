package org.sjhstudio.naverwebtoon.ui.weekday.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.naverwebtoon.databinding.ItemNewWebtoonBinding
import org.sjhstudio.naverwebtoon.domain.model.NewWebToon
import org.sjhstudio.naverwebtoon.util.getStatusBarHeight

class NewWebToonAdapter :
    ListAdapter<NewWebToon, NewWebToonAdapter.NewWebToonViewHolder>(diffCallback) {

    class NewWebToonViewHolder(private val binding: ItemNewWebtoonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                tvTitle.setPadding(0, tvTitle.context.getStatusBarHeight(), 0, 0)
            }
        }

        fun bind(data: NewWebToon) {
            with(binding) {
                newWebToon = data
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewWebToonViewHolder {
        val binding =
            ItemNewWebtoonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewWebToonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewWebToonViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<NewWebToon>() {
            override fun areItemsTheSame(oldItem: NewWebToon, newItem: NewWebToon): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NewWebToon, newItem: NewWebToon): Boolean {
                return oldItem == newItem
            }
        }
    }
}