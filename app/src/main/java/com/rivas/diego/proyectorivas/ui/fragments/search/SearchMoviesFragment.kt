package com.rivas.diego.proyectorivas.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivas.diego.proyectorivas.databinding.FragmentSearchMoviesBinding
import com.rivas.diego.proyectorivas.ui.adapters.MovieSearchAdapter

class SearchMoviesFragment : Fragment() {

    private lateinit var binding: FragmentSearchMoviesBinding
    private lateinit var adapter: MovieSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieSearchAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        // Implementa aquí la lógica de búsqueda y carga de datos
        performSearch("query")
    }

    private fun performSearch(query: String) {
        // Lógica para realizar la búsqueda y actualizar el adaptador
    }
}
