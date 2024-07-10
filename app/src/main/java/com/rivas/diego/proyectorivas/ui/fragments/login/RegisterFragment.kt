package com.rivas.diego.proyectorivas.ui.fragments.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.data.local.repository.DataBaseRepository
import com.rivas.diego.proyectorivas.databinding.FragmentRegisterBinding
import com.rivas.diego.proyectorivas.logic.usercase.login.CreateUserWithNameAndPassword
import com.rivas.diego.proyectorivas.ui.core.ManageUIStates
import com.rivas.diego.proyectorivas.ui.core.UIStates
import com.rivas.diego.proyectorivas.ui.viewmodels.login.RegisterFragmentVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private lateinit var con: DataBaseRepository

    private val registerFragmentVM: RegisterFragmentVM by viewModels()

    private lateinit var managerUIStates: ManageUIStates

    private lateinit var auth: FirebaseAuth


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
        initVariables()
    }

    private fun initVariables() {

        //lA INICIALIZO
        managerUIStates= ManageUIStates(requireActivity(),binding.lytLoading.mainLayout)

        //
        // Initialize Firebase Auth
        auth = Firebase.auth

    }

    private fun initiObservers() {

        registerFragmentVM.uiState.observe(viewLifecycleOwner){states->
            managerUIStates.invoke(states)


        }

    }


    private fun initiListeners() {

       binding.btnreverse.setOnClickListener{
           findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
       }

        binding.btnSave.setOnClickListener{
            //Recibe dos parametros y espera un CallBack
            auth.signInWithEmailAndPassword(binding.etxtUser.text.toString(), binding.etxtPass.text.toString())
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {

                        Log.d("TAG", "signInWithEmail:success")
                        val user = auth.currentUser

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            requireActivity(),
                            task.exception?.message.toString(),
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }

            /*

            ESTO PASARLE A OTRO METODO
            //++++++++++
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle("Informacion")
                .setMessage("Seguro de que desea guardar la informacion proporcionada?")
                .setPositiveButton("Si"){dialog,id->
                    binding.btnSave.setOnClickListener{
                        registerFragmentVM.saveUser(
                            binding.etxtUser.text.toString(),
                            binding.etxtPass.text.toString(),
                            requireContext()
                        )
                        dialog.dismiss()
                    }
                }
                .setNegativeButton("No"){dialog, id->
                    dialog.cancel()
                }
                .show()

             */

            //+++++++++++++

        }



    }

}

