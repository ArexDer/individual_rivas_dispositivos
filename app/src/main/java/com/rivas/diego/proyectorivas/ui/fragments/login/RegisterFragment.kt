package com.rivas.diego.proyectorivas.ui.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.data.local.repository.DataBaseRepository
import com.rivas.diego.proyectorivas.databinding.FragmentRegisterBinding
import com.rivas.diego.proyectorivas.logic.usercase.login.CreateUserWithNameAndPassword
import com.rivas.diego.proyectorivas.ui.viewmodels.login.RegisterFragmentVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private lateinit var con: DataBaseRepository

    private val registerFragmentVM: RegisterFragmentVM by viewModels()


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

        initiListeners()
        initiObservers()
    }

    private fun initiObservers() {
       registerFragmentVM.userSaved.observe(viewLifecycleOwner){
           res->if(res){
               binding.etxtUser.setText("")
               binding.etxtPass.setText("")

           Snackbar.make(binding.etxtUser,"CREATED USER ..!",Snackbar.LENGTH_LONG).show()
       }else{
           Snackbar.make(binding.etxtUser,"ERROR USER ..!",Snackbar.LENGTH_LONG).show()
       }
       }
    }

    private fun initiListeners() {
       binding.btnreverse.setOnClickListener{
           findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
       }


        binding.btnSave.setOnClickListener{
lifecycleScope.launch {
    registerFragmentVM.saveUser(
        binding.etxtUser.text.toString(),
        binding.etxtPass.text.toString(),
        requireContext()

    )
}



        }
    }



}

