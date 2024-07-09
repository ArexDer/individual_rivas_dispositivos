package com.rivas.diego.proyectorivas.ui.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.databinding.FragmentListarMoviesPopularityBinding
import com.rivas.diego.proyectorivas.databinding.FragmentModificarBinding
import com.rivas.diego.proyectorivas.logic.usercase.movie.GetMovieInfoPopularityUserCase
import com.rivas.diego.proyectorivas.ui.adapters.ListarMoviesPopularityAdapter
import com.rivas.diego.proyectorivas.ui.core.ManageUIStates
import com.rivas.diego.proyectorivas.ui.viewmodels.main.ListarMovieInfoVM

class ListarMoviesPopularityFragment : Fragment() {


    private lateinit var binding: FragmentListarMoviesPopularityBinding

    private lateinit var  adapter: ListarMoviesPopularityAdapter
    private val listarMovieInfoVM: ListarMovieInfoVM by viewModels()
    private lateinit var manageUIStates: ManageUIStates

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentListarMoviesPopularityBinding.bind(inflater.inflate(
            R.layout.fragment_listar_movies_popularity,container,false)
        )
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVariables()
        initListeners()
        initObservers()

        initData()
    }

    private fun initVariables(){
        manageUIStates = ManageUIStates(requireActivity(), binding.lytLoading.mainLayout)
        adapter= ListarMoviesPopularityAdapter()
        binding.rvListMovieInfo.adapter=adapter
        binding.rvListMovieInfo.layoutManager=LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL,
            false
        )
    }
    private fun initListeners(){}
    private fun initObservers() {
        listarMovieInfoVM.itemsMovies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        listarMovieInfoVM.uiState.observe(viewLifecycleOwner) {
            manageUIStates.invoke(it)
        }

    }

    private fun initData(){
        listarMovieInfoVM.initData()
        Log.d("TAG", "Iniciando Datos...!")
    }



}