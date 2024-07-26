package com.rivas.diego.proyectorivas.data.network.entities.search

data class SearchResultsAPI(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)