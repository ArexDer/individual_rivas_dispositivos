package com.rivas.diego.proyectorivas.data.network.entities.moviesupcoming

data class UpcomingAPI(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)