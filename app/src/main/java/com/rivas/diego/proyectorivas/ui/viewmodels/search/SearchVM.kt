package com.rivas.diego.proyectorivas.ui.viewmodels.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivas.diego.proyectorivas.data.network.endpoints.MoviesEndPoints
import com.rivas.diego.proyectorivas.data.network.entities.movies.MoviesAPI
import com.rivas.diego.proyectorivas.data.network.entities.tv.TVShowsAPI
import com.rivas.diego.proyectorivas.ui.entities.search.SearchResultUI
import kotlinx.coroutines.launch

class SearchVM(private val moviesEndPoints: MoviesEndPoints) : ViewModel() {

    private val _searchResults = MutableLiveData<List<SearchResultUI>>()
    val searchResults: LiveData<List<SearchResultUI>> = _searchResults

    fun search(query: String, filter: String) {
        viewModelScope.launch {
            try {
                val response = when (filter) {
                    "movie" -> moviesEndPoints.searchMovies(query)
                    "tv" -> moviesEndPoints.searchTVShows(query)
                    // "person" -> moviesEndPoints.searchPeople(query)
                    else -> throw IllegalArgumentException("Invalid filter")
                }

                if (response.isSuccessful) {
                    val searchResults = when (filter) {
                        "movie" -> (response.body() as? MoviesAPI)?.results?.map {
                            SearchResultUI(
                                id = it.id,
                                title = it.title,
                                name = null,
                                posterPath = it.poster_path,
                                overview = it.overview
                            )
                        }

                        "tv" -> (response.body() as? TVShowsAPI)?.results?.map {
                            SearchResultUI(
                                id = it.id,
                                title = null,
                                name = it.name,
                                posterPath = it.poster_path,
                                overview = it.overview
                            )
                        }

                        // "person" -> (response.body() as? PeopleAPI)?.results?.map {
                        //     SearchResultUI(
                        //         id = it.id,
                        //         title = null,
                        //         name = it.name,
                        //         posterPath = it.profile_path,
                        //         overview = it.biography
                        //     )
                        // }

                        else -> emptyList()
                    }

                    _searchResults.value = searchResults ?: emptyList()
                } else {
                    // Handle errors here
                }
            } catch (e: Exception) {
                // Handle exceptions here
            }
        }
    }
}
