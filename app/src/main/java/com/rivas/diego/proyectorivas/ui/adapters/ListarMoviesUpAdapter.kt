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
import com.rivas.diego.proyectorivas.ui.entities.movies.MoviesUpUI

class ListarMoviesUpAdapter(
    private val onItemClick: (MoviesUpUI) -> Unit
) : ListAdapter<MoviesUpUI, ListarMoviesUpAdapter.MovieVH>(DiffUtilMoviesUpCallback) {

    inner class MovieVH(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMoviesInfoBinding.bind(view)

        fun render(item: MoviesUpUI) {
            binding.imageView.load("https://image.tmdb.org/t/p/w500" + item.poster_path)
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val inflater = LayoutInflater.from(parent.context)
        return MovieVH(
            inflater.inflate(
                R.layout.item_movies_info,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.render(getItem(position))
    }
}

object DiffUtilMoviesUpCallback : DiffUtil.ItemCallback<MoviesUpUI>() {
    override fun areItemsTheSame(oldItem: MoviesUpUI, newItem: MoviesUpUI): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: MoviesUpUI, newItem: MoviesUpUI): Boolean {
        return oldItem == newItem
    }
}
