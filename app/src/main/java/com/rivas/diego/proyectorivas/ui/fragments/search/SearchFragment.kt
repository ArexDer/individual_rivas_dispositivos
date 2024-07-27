package com.rivas.diego.proyectorivas.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.databinding.FragmentSearchBinding
import com.rivas.diego.proyectorivas.ui.adapters.SearchResultsAdapter
import com.rivas.diego.proyectorivas.ui.viewmodels.search.SearchVM
import com.rivas.diego.proyectorivas.data.network.repository.NetworkService
import com.rivas.diego.proyectorivas.ui.entities.search.SearchResultUI
import com.rivas.diego.proyectorivas.ui.viewmodels.search.SearchVMFactory

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchResultsAdapter

    private val searchVM: SearchVM by viewModels { SearchVMFactory(NetworkService.moviesEndPoints) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchResultsAdapter()
        binding.rvSearchResults.adapter = adapter
        binding.rvSearchResults.layoutManager = LinearLayoutManager(context)

        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()
            val filter = getSelectedFilter()
            searchVM.search(query, filter)
        }

        searchVM.searchResults.observe(viewLifecycleOwner) { results ->
            adapter.submitList(results)
        }

        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_searchFragment_to_moviesFragment2)

        }


    }

    private fun getSelectedFilter(): String {
        return when {
            binding.rbMovies.isChecked -> "movie"
            binding.rbTVShows.isChecked -> "tv"
           // binding.rbPeople.isChecked -> "person"
            else -> "movie" // Default filter if none is selected
        }
    }
}
