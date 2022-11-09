package org.sjhstudio.naverwebtoon.ui.episode.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.naverwebtoon.databinding.ItemEpisodeBinding
import org.sjhstudio.naverwebtoon.domain.model.Episode

class EpisodeAdapter(
    private val onClicked: (episode: Episode) -> Unit
) : PagingDataAdapter<Episode, EpisodeAdapter.EpisodeViewHolder>(diffCallback) {

    inner class EpisodeViewHolder(private val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: Episode) {
            with(binding) {
                data = episode
                setClickListener {
                    onClicked(episode)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode) =
                oldItem.dataNo == newItem.dataNo

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode) =
                oldItem == newItem
        }
    }
}