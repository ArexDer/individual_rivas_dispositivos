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
import com.rivas.diego.proyectorivas.ui.entities.movies.MoviesInfoUI
import com.rivas.diego.proyectorivas.ui.entities.movies.MoviesUpUI

class ListarMoviesUpAdapter:
    ListAdapter<MoviesUpUI, ListarMoviesUpAdapter.MovieUpVH>(DiffUtilMovieUpCallback){

    class MovieUpVH(view: View): RecyclerView.ViewHolder(view){
        private val binding= ItemMoviesInfoBinding.bind(view)

        fun render(item: MoviesUpUI){
            binding.imageView.load("https://image.tmdb.org/t/p/w500"+item.poster_path)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieUpVH {
        val inflater = LayoutInflater.from(parent.context)
        return MovieUpVH(
            inflater.inflate(
                R.layout.item_movies_info,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieUpVH, position: Int) {
        holder.render(
            getItem(position)
        )
    }


}

object DiffUtilMovieUpCallback : DiffUtil.ItemCallback<MoviesUpUI>() {
    override fun areItemsTheSame(oldItem: MoviesUpUI, newItem: MoviesUpUI):
            Boolean {
        return oldItem.id == newItem.id

    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: MoviesUpUI, newItem: MoviesUpUI):
            Boolean {
        return oldItem == newItem
    }

}