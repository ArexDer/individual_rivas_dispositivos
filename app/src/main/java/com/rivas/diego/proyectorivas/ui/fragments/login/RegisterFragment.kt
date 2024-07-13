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
import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.data.local.repository.DataBaseRepository
import com.rivas.diego.proyectorivas.databinding.FragmentRegisterBinding
import com.rivas.diego.proyectorivas.logic.usercase.login.CreateUserWithNameAndPassword
import com.rivas.diego.proyectorivas.ui.core.ManageUIStates
import com.rivas.diego.proyectorivas.ui.viewmodels.login.RegisterFragmentVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

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

            Log.d("TAG", "Email: $email, Password: $password")

            auth.createUserWithEmailAndPassword(binding.txtEmail.text.toString().trim(), binding.txtPass.text.toString().trim())
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "signInWithEmail:success")
                        val user = auth.currentUser
                        // Continúa con el flujo de tu aplicación
                    } else {
                        Log.d("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(requireActivity(), task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun createLocalUser() {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Informacion")
            .setMessage("Seguro de que desea guardar la informacion proporcionada?")
            .setPositiveButton("Si") { dialog, id ->
                binding.btnSave.setOnClickListener {
                    registerFragmentVM.saveUser(
                        binding.txtEmail.text.toString().trim(),
                        binding.txtPass.text.toString().trim(),
                        requireContext()
                    )
                    dialog.dismiss()
                }
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.cancel()
            }
            .show()
    }
}
