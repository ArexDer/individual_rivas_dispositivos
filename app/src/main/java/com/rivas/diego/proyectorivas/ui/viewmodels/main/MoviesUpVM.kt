package com.rivas.diego.proyectorivas.ui.viewmodels.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.rivas.diego.proyectorivas.logic.usercase.movie.GetMovieUpcomingUserCase
import com.rivas.diego.proyectorivas.ui.core.UIStates
import com.rivas.diego.proyectorivas.ui.entities.movies.MoviesInfoUI
import com.rivas.diego.proyectorivas.ui.entities.movies.MoviesUpUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MoviesUpVM: ViewModel() {
    val itemsMoviesUp = MutableLiveData<List<MoviesUpUI>>()
    val uiState = MutableLiveData<UIStates>()

    fun initData() {
        Log.d("TAG", "Ingresando al VM")
        viewModelScope.launch {
            uiState.postValue(UIStates.Loading(true))
            GetMovieUpcomingUserCase().invoke().collect{
                    respuesta-> respuesta.onSuccess { items->
                itemsMoviesUp.postValue(items)
            }
                respuesta.onFailure { uiState.postValue(UIStates.Error(it.message.toString()))
                    Log.d("TAG",it.message.toString())
                }
            }
            delay(500)
            uiState.postValue(UIStates.Loading(false))

        }
    }
}