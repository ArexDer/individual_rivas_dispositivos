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
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.databinding.DialogMovieInfoBinding
import com.rivas.diego.proyectorivas.databinding.FragmentMoviesBinding
import com.rivas.diego.proyectorivas.ui.adapters.ListarMoviesPopularityAdapter
import com.rivas.diego.proyectorivas.ui.adapters.ListarMoviesUpAdapter
import com.rivas.diego.proyectorivas.ui.adapters.ListarTVSeriesAdapter
import com.rivas.diego.proyectorivas.ui.entities.movies.MoviesInfoUI
import com.rivas.diego.proyectorivas.ui.core.ManageUIStates
import com.rivas.diego.proyectorivas.ui.entities.movies.MoviesUpUI
import com.rivas.diego.proyectorivas.ui.entities.tv.TVSeriesUI
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

        binding.fabSearch.setOnClickListener {
            findNavController().navigate(R.id.action_moviesFragment2_to_searchFragment)
        }

    }

    private fun initVariables() {
        manageUIStates = ManageUIStates(requireActivity(), binding.lytLoading.mainLayout)

        adapter = ListarMoviesPopularityAdapter { movie -> showMovieInfoDialog(movie) }
        adapterMoviesUp = ListarMoviesUpAdapter { movieUp -> showMovieUpDialog(movieUp) }
        adapterTV = ListarTVSeriesAdapter { tvSeries -> showTVSeriesDialog(tvSeries) }

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

    private fun showMovieInfoDialog(movie: MoviesInfoUI) {
        // Crear una nueva instancia del binding para evitar la reutilización
        val dialogBinding = DialogMovieInfoBinding.inflate(LayoutInflater.from(requireContext()))

        // Configurar el contenido del diálogo
        dialogBinding.ivBackground.load("https://image.tmdb.org/t/p/w500" + movie.poster_path) {
            crossfade(true)
        }
        dialogBinding.tvTitle.text = movie.title
        dialogBinding.tvOriginalLanguage.text = movie.original_language
        dialogBinding.tvPopularity.text = movie.popularity.toString()
        dialogBinding.tvSynopsis.text = movie.overview

        // Configurar el VideoView si hay un video
        if (movie.video) {
            // Aquí deberías obtener la URL real del video
            val videoUrl = "https://path.to/video.mp4" // Reemplaza con la URL real del video
            dialogBinding.videoView.setVideoPath(videoUrl)
            dialogBinding.videoView.setMediaController(android.widget.MediaController(requireContext()))
            dialogBinding.videoView.visibility = View.VISIBLE
        } else {
            dialogBinding.videoView.visibility = View.GONE
        }

        // Crear y mostrar el AlertDialog
        MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showMovieUpDialog(movieUp: MoviesUpUI) {
        // Crear una nueva instancia del binding para evitar la reutilización
        val dialogBinding = DialogMovieInfoBinding.inflate(LayoutInflater.from(requireContext()))

        dialogBinding.ivBackground.load("https://image.tmdb.org/t/p/w500" + movieUp.poster_path) {
            crossfade(true)
        }
        dialogBinding.tvTitle.text = movieUp.title
        dialogBinding.tvOriginalLanguage.text = movieUp.original_language
        dialogBinding.tvPopularity.text = movieUp.popularity.toString()
        dialogBinding.tvSynopsis.text = movieUp.overview

        // Configurar el VideoView si hay un video
        if (movieUp.video) {
            // Aquí deberías obtener la URL real del video
            val videoUrl = "https://path.to/video.mp4" // Reemplaza con la URL real del video
            dialogBinding.videoView.setVideoPath(videoUrl)
            dialogBinding.videoView.setMediaController(android.widget.MediaController(requireContext()))
            dialogBinding.videoView.visibility = View.VISIBLE
        } else {
            dialogBinding.videoView.visibility = View.GONE
        }

        // Crear y mostrar el AlertDialog
        MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showTVSeriesDialog(tvSeries: TVSeriesUI) {
        // Crear una nueva instancia del binding para evitar la reutilización
        val dialogBinding = DialogMovieInfoBinding.inflate(LayoutInflater.from(requireContext()))

        dialogBinding.ivBackground.load("https://image.tmdb.org/t/p/w500" + tvSeries.poster_path) {
            crossfade(true)
        }
        dialogBinding.tvTitle.text = tvSeries.name
        dialogBinding.tvOriginalLanguage.text = tvSeries.original_name
        dialogBinding.tvPopularity.text = tvSeries.popularity.toString()
        dialogBinding.tvSynopsis.text = tvSeries.overview

        // Crear y mostrar el AlertDialog
        MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("OK", null)
            .show()
    }

}
