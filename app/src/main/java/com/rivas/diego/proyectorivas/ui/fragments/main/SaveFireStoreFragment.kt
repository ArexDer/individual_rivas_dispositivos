package com.rivas.diego.proyectorivas.ui.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject

import com.google.firebase.ktx.Firebase


import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.databinding.FragmentSaveFireStoreBinding
import com.rivas.diego.proyectorivas.ui.core.ManageUIStates
import com.rivas.diego.proyectorivas.ui.core.UIStates
import com.rivas.diego.proyectorivas.ui.entities.users.UserLogin
import com.rivas.diego.proyectorivas.ui.viewmodels.login.SaveFireStoreVM


class SaveFireStoreFragment : Fragment() {

    private lateinit var binding: FragmentSaveFireStoreBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth:FirebaseAuth

    private lateinit var manageUIState:ManageUIStates


    //Hago la referencia al VM
    private val saveFireStoreVM : SaveFireStoreVM by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSaveFireStoreBinding.bind(inflater.inflate(R.layout.fragment_save_fire_store
        ,container,false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db= Firebase.firestore
        auth=Firebase.auth

        //Inicializo
        manageUIState = ManageUIStates(requireActivity(),binding.lytLoading.mainLayout)

        initListeners()
        initObservers()
    }

    private fun initObservers() {
        saveFireStoreVM.userUI.observe(viewLifecycleOwner){state->

            manageUIState.invoke(state)

        }

        saveFireStoreVM.userUI.observe(viewLifecycleOwner){
                state-> manageUIState.invoke(state)


        }
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener{
            val user = UserLogin(
                auth.currentUser!!.uid, //Con esto anexo el email con los demas dato del usuario.
                binding.Name.text.toString(),
                binding.LastName.text.toString()
            )

            saveFireStoreVM.saveUserFireStore(user)

        }

        binding.btnGet.setOnClickListener{


            saveFireStoreVM.getUserByIdFireStore(auth.currentUser!!.uid)

        }
    }


}