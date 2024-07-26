package com.rivas.diego.proyectorivas.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.databinding.ItemMoviesInfoBinding
import com.rivas.diego.proyectorivas.ui.entities.tv.TVSeriesUI

class ListarTVSeriesAdapter(
    private val onItemClick: (TVSeriesUI) -> Unit
) : ListAdapter<TVSeriesUI, ListarTVSeriesAdapter.TVSeriesVH>(DiffUtilTVCallback) {

    inner class TVSeriesVH(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMoviesInfoBinding.bind(view)

        fun render(item: TVSeriesUI) {
            binding.imageView.load("https://image.tmdb.org/t/p/w500" + item.poster_path)
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVSeriesVH {
        val inflater = LayoutInflater.from(parent.context)
        return TVSeriesVH(
            inflater.inflate(
                R.layout.item_movies_info,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TVSeriesVH, position: Int) {
        holder.render(getItem(position))
    }
}

object DiffUtilTVCallback : DiffUtil.ItemCallback<TVSeriesUI>() {
    override fun areItemsTheSame(oldItem: TVSeriesUI, newItem: TVSeriesUI): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: TVSeriesUI, newItem: TVSeriesUI): Boolean {
        return oldItem == newItem
    }
}
