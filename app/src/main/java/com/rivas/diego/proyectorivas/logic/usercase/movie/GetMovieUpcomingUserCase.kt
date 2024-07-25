package com.rivas.diego.proyectorivas.logic.usercase.movie

import android.util.Log
import com.rivas.diego.proyectorivas.data.network.endpoints.MoviesEndPoints
import com.rivas.diego.proyectorivas.data.network.repository.RetrofitBase
import com.rivas.diego.proyectorivas.ui.core.toMoviesInfoUI
import com.rivas.diego.proyectorivas.ui.core.toUpcomingAPI
import com.rivas.diego.proyectorivas.ui.entities.movies.MoviesInfoUI
import com.rivas.diego.proyectorivas.ui.entities.movies.MoviesUpUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class GetMovieUpcomingUserCase {

    suspend operator fun invoke() = flow {

        var response = RetrofitBase.returnBaseRetrofitMovies()
            .create(MoviesEndPoints::class.java)
            .getUpcomingMovies("en-US")

        Log.d("stop", response.toString())
        if (response.isSuccessful) {

            val x = response.body()?.results

            var items = ArrayList<MoviesUpUI>()

            x?.forEach {
                items.add(it.toUpcomingAPI())
            }
            Log.d("TAG", items.size.toString())
            emit(Result.success(items.toList()))
        }

    }.catch {
        Log.d("TAG", it.message.toString())
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}