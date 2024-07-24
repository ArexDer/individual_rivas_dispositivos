package com.rivas.diego.proyectorivas.ui.fragments.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.databinding.FragmentRegisterBinding
import com.rivas.diego.proyectorivas.ui.core.ManageUIStates
import com.rivas.diego.proyectorivas.ui.viewmodels.login.RegisterFragmentVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val registerFragmentVM: RegisterFragmentVM by viewModels()
    private lateinit var managerUIStates: ManageUIStates
    private lateinit var auth: FirebaseAuth
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

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
        initVariables()
        initListeners()
        initObservers()
    }

    private fun initVariables() {
        managerUIStates = ManageUIStates(requireActivity(), binding.lytLoading.mainLayout)
        auth = FirebaseAuth.getInstance()
    }

    private fun initObservers() {
        registerFragmentVM.uiState.observe(viewLifecycleOwner) { states ->
            managerUIStates.invoke(states)
        }
    }

    private fun initListeners() {
        binding.btnreverse.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        binding.btnSave.setOnClickListener {
            val email = binding.txtEmail.text.toString().trim()
            val password = binding.txtPass.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireActivity(), "Email y contraseña no pueden estar vacíos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        saveUserToFirestore()
                    } else {
                        Toast.makeText(requireActivity(), task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun saveUserToFirestore() {
        val user = auth.currentUser ?: return
        val userData = hashMapOf(
            "firstName" to binding.txtFirstName.text.toString().trim(),
            "lastName" to binding.txtLastName.text.toString().trim(),
            "age" to binding.txtAge.text.toString().trim(),
            "nickname" to binding.txtNickname.text.toString().trim(),
            "email" to binding.txtEmail.text.toString().trim()
        )

        firestore.collection("users").document(user.uid).set(userData)
            .addOnSuccessListener {
                Toast.makeText(requireActivity(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireActivity(), e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}
