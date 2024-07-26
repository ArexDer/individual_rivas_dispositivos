package com.rivas.diego.proyectorivas.ui.adapters

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rivas.diego.proyectorivas.R

class MovieSearchAdapter : RecyclerView.Adapter<MovieSearchAdapter.MovieViewHolder>() {

    private val movies = mutableListOf<Movie>() // Define tu modelo de película aquí

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies_info, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        // Configura las vistas con los datos de la película
    }

    override fun getItemCount(): Int = movies.size

    fun submitList(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }
}