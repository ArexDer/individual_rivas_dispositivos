package com.rivas.diego.proyectorivas.data.network.endpoints

import com.rivas.diego.proyectorivas.data.network.entities.movies.MoviesAPI
import com.rivas.diego.proyectorivas.data.network.entities.moviesupcoming.UpcomingAPI
import com.rivas.diego.proyectorivas.data.network.entities.search.SearchResultsAPI
import com.rivas.diego.proyectorivas.data.network.entities.tv.TVShowsAPI
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesEndPoints {
    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "es-MX",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"

    ): Response<MoviesAPI>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String = "es-MX",
        @Query("page") page: Int = 1
    ): Response<UpcomingAPI>

    @GET("discover/tv")
    suspend fun getdiscoverTVShows(
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("include_null_first_air_dates") includeNullFirstAirDates: Boolean = false,
        @Query("language") language: String = "es-MX",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): Response<TVShowsAPI>


    @GET("search/multi")
    suspend fun search(
        @Query("query") query: String,
        @Query("language") language: String = "es-MX",
        @Query("page") page: Int = 1
    ): Response<SearchResultsAPI>


    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("language") language: String = "es-MX",
        @Query("page") page: Int = 1
    ): Response<MoviesAPI> // Reemplaza con la clase correcta para resultados de búsqueda de películas

    @GET("search/tv")
    suspend fun searchTVShows(
        @Query("query") query: String,
        @Query("language") language: String = "es-MX",
        @Query("page") page: Int = 1
    ): Response<TVShowsAPI> // Reemplaza con la clase correcta para resultados de búsqueda de TV shows

//    @GET("search/person")
//    suspend fun searchPeople(
//        @Query("query") query: String,
//        @Query("language") language: String = "es-MX",
//        @Query("page") page: Int = 1
//    ): Response<PeopleAPI> // Asegúrate de tener esta clase para resultados de búsqueda de personas


}

