package com.rivas.diego.proyectorivas.ui.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.databinding.FragmentMoviesBinding
import com.rivas.diego.proyectorivas.ui.adapters.ListarMoviesPopularityAdapter
import com.rivas.diego.proyectorivas.ui.adapters.ListarMoviesUpAdapter
import com.rivas.diego.proyectorivas.ui.adapters.ListarTVSeriesAdapter
import com.rivas.diego.proyectorivas.ui.core.ManageUIStates
import com.rivas.diego.proyectorivas.ui.viewmodels.main.MoviesUpVM
import com.rivas.diego.proyectorivas.ui.viewmodels.main.MoviesVM
import com.rivas.diego.proyectorivas.ui.viewmodels.main.TVSeriesVM

class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    private lateinit var adapter: ListarMoviesPopularityAdapter
    private lateinit var adapterMoviesUp: ListarMoviesUpAdapter
    private lateinit var adapterTV: ListarTVSeriesAdapter


    private val moviesVM: MoviesVM by viewModels()
    private val moviesUpVM: MoviesUpVM by viewModels()
    private val tvVM: TVSeriesVM by viewModels()

    private lateinit var manageUIStates: ManageUIStates

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Inicializar variables, oyentes y observadores
        initVariables()
        initListeners()
        initObservers()
        initData()

        // Cargar el nickname del usuario
        loadNickname()
    }

    private fun initData() {
        moviesVM.initData()
        moviesUpVM.initData()
        tvVM.initData()
        Log.d("TAG", "Iniciando Datos...!")
    }

    private fun initObservers() {
        moviesVM.itemsMovies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        moviesVM.uiState.observe(viewLifecycleOwner) {
            manageUIStates.invoke(it)
        }

        //---------------
        moviesUpVM.itemsMoviesUp.observe(viewLifecycleOwner) {
            adapterMoviesUp.submitList(it)
        }

        moviesUpVM.uiState.observe(viewLifecycleOwner) {
            manageUIStates.invoke(it)
        }

        //-----------------
        tvVM.itemsTVSeries.observe(viewLifecycleOwner) {
            adapterTV.submitList(it)
        }

        tvVM.uiState.observe(viewLifecycleOwner) {
            manageUIStates.invoke(it)
        }


    }

    private fun initListeners() {
        binding.ivProfilePicture.setOnClickListener {
            findNavController().navigate(R.id.action_moviesFragment2_to_profileSettingsFragment2)
        }


    }

    private fun initVariables() {
        manageUIStates = ManageUIStates(requireActivity(), binding.lytLoading.mainLayout)
        adapter = ListarMoviesPopularityAdapter()
        adapterMoviesUp=ListarMoviesUpAdapter()
        adapterTV=ListarTVSeriesAdapter()

        binding.rvDiscover.adapter = adapter
        binding.rvDiscover.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.HORIZONTAL, false
        )

        binding.rvRanking.adapter = adapterTV
        binding.rvRanking.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.HORIZONTAL, false
        )

        binding.rvComingSoon.adapter = adapterMoviesUp
        binding.rvComingSoon.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.HORIZONTAL, false
        )
    }

    private fun loadNickname() {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("users").document(user.uid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val nickname = document.getString("nickname") ?: "Nickname"
                    binding.tvNickname.text = nickname
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to load nickname: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


}
