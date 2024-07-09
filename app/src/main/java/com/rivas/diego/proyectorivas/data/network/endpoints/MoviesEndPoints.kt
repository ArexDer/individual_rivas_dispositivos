package com.rivas.diego.proyectorivas.data.network.endpoints

import com.rivas.diego.proyectorivas.data.network.entities.movies.MoviesAPI
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesEndPoints {
    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"

    ): Response<MoviesAPI>
}

//IMPORTANTE

//REVISSAR PORQUE DONDE ESTA "Resposne" era Call y la funcion no tiene suspend