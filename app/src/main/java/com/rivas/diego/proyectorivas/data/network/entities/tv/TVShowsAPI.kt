package com.rivas.diego.proyectorivas.data.network.entities.tv

data class TVShowsAPI(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)