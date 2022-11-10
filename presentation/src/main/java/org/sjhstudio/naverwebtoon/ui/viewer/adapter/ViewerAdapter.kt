package org.sjhstudio.naverwebtoon.ui.viewer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.naverwebtoon.databinding.ItemViewerBinding

class ViewerAdapter : PagingDataAdapter<String, ViewerAdapter.ViewerViewHolder>(diffCallback) {

    inner class ViewerViewHolder(private val binding: ItemViewerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            with(binding) {
                data = imageUrl
            }
        }
    }

    override fun onBindViewHolder(holder: ViewerViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewerViewHolder {
        val binding = ItemViewerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewerViewHolder(binding)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        }
    }
}