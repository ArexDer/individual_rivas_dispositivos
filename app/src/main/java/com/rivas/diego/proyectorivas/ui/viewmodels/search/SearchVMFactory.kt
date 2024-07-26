package com.rivas.diego.proyectorivas.ui.viewmodels.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rivas.diego.proyectorivas.data.network.endpoints.MoviesEndPoints

class SearchVMFactory(private val moviesEndPoints: MoviesEndPoints) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchVM::class.java)) {
            return SearchVM(moviesEndPoints) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
