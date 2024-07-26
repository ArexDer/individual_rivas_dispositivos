package com.rivas.diego.proyectorivas.ui.core
import com.rivas.diego.proyectorivas.data.network.entities.search.Result
import com.rivas.diego.proyectorivas.ui.entities.search.SearchResultUI

fun Result.toSearchResultUI()= SearchResultUI(
    this.id,
    this.title,
    this.name,
    this.poster_path,
    this.overview

)