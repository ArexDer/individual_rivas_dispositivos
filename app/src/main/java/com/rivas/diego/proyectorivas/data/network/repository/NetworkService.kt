

package com.rivas.diego.proyectorivas.data.network.repository

import com.rivas.diego.proyectorivas.data.network.endpoints.MoviesEndPoints

object NetworkService {

    private val retrofit = RetrofitBase.returnBaseRetrofitMovies()

    val moviesEndPoints: MoviesEndPoints = retrofit.create(MoviesEndPoints::class.java)
}
