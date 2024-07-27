package com.rivas.diego.proyectorivas.ui.entities.movies

data class MoviesInfoUI (

    val id: Int,
    val original_language: String,
    val original_title: String,
    val title: String,
    val popularity: Double,
    val poster_path: String,
    val overview: String,
    val video: Boolean,

)