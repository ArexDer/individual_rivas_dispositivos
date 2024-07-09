package com.rivas.diego.proyectorivas.data.network.repository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBase {

    // CREACION DE LA BASE URL DE TMBD MOVIES
    fun returnBaseRetrofitMovies(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(apiMovies())
            .build()
    }

    private fun apiMovies(): OkHttpClient = OkHttpClient.Builder().addInterceptor(MoviesInterceptor()).build()
}