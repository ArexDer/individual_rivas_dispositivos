package com.rivas.diego.proyectorivas.ui.core
import  com.rivas.diego.proyectorivas.data.network.entities.moviesupcoming.Result
import com.rivas.diego.proyectorivas.ui.entities.movies.MoviesUpUI

fun Result.toUpcomingAPI()= MoviesUpUI (

    this.id,
    this.original_language,
    this.original_title,
    this.title,
    this.popularity,
    this.poster_path,
    this.overview,
    this.video
)