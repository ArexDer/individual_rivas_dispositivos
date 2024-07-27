package com.rivas.diego.proyectorivas.ui.core

import com.rivas.diego.proyectorivas.data.network.entities.movies.Result
import com.rivas.diego.proyectorivas.ui.entities.movies.MoviesInfoUI

fun Result.toMoviesInfoUI() = MoviesInfoUI(

    this.id,
    this.original_language,
    this.original_title,
    this.title,
    this.popularity,
    this.poster_path,
    this.overview,
    this.video
)