package com.rivas.diego.proyectorivas.ui.core

import com.rivas.diego.proyectorivas.ui.entities.tv.TVSeriesUI
import com.rivas.diego.proyectorivas.data.network.entities.tv.Result

fun Result.toTVSeriesUI()= TVSeriesUI (

    this.id,
    this.name,
    this.original_name,
    this.popularity,
    this.poster_path,
    this.vote_average,
    this.vote_count,
    this.overview
)
