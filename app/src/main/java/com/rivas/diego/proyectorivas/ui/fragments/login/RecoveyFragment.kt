package com.rivas.diego.proyectorivas.ui.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.databinding.FragmentRecoveyBinding

class RecoveyFragment : Fragment() {
    private lateinit var binding: FragmentRecoveyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentRecoveyBinding.bind(

            inflater.inflate(R.layout.fragment_recovey, container, false))
        return binding.root
        }

}
