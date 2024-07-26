package com.rivas.diego.proyectorivas.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rivas.diego.proyectorivas.databinding.ItemSearchResultBinding
import com.rivas.diego.proyectorivas.ui.entities.search.SearchResultUI


class SearchResultsAdapter : ListAdapter<SearchResultUI, SearchResultsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(private val binding: ItemSearchResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchResultUI) {
            binding.tvTitle.text = item.title ?: item.name
            binding.ivPoster.load("https://image.tmdb.org/t/p/w500${item.posterPath}")
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<SearchResultUI>() {
        override fun areItemsTheSame(oldItem: SearchResultUI, newItem: SearchResultUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchResultUI, newItem: SearchResultUI): Boolean {
            return oldItem == newItem
        }
    }
}
