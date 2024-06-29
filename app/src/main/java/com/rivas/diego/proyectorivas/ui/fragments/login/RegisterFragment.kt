package com.rivas.diego.proyectorivas.ui.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.data.local.repository.DataBaseRepository
import com.rivas.diego.proyectorivas.databinding.FragmentRegisterBinding
import com.rivas.diego.proyectorivas.ui.core.MyApplication

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private lateinit var con: DataBaseRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.bind(
            inflater.inflate(R.layout.fragment_register, container, false)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        con = MyApplication.getDBConnection()

    }

}