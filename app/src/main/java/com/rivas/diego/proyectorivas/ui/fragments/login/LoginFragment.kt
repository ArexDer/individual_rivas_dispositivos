package com.rivas.diego.proyectorivas.ui.fragments.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.databinding.FragmentLoginBinding
import com.rivas.diego.proyectorivas.ui.activities.MainActivity
import com.rivas.diego.proyectorivas.ui.core.ManageUIStates
import com.rivas.diego.proyectorivas.ui.core.UIStates
import com.rivas.diego.proyectorivas.ui.viewmodels.login.LoginFragmentVM
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var biometricManager: BiometricManager
    private lateinit var managerUIStates: ManageUIStates
    private val loginFragmentVM: LoginFragmentVM by viewModels()
    private lateinit var auth: FirebaseAuth

    // PARA GUARDAR MIS CREDENCIALES
    private lateinit var sharedPreferences: SharedPreferences
    private val sharedPreferencesEditor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVariables()
        initListeners()
        initiObservers()
    }

    private fun initVariables() {
        auth = FirebaseAuth.getInstance()
        managerUIStates = ManageUIStates(requireActivity(), binding.lytLoading.mainLayout)
        // Inicializa SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    }

    private fun initiObservers() {
        loginFragmentVM.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIStates.Success -> fetchNicknameAndNavigate()
                is UIStates.Error -> {
                    Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
                    managerUIStates.invoke(state)
                }
                else -> managerUIStates.invoke(state)
            }
        }
    }

    private fun initListeners() {
        binding.btnSigIn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.imgFinger.setOnClickListener {
            initBiometric()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etxtUser.text.toString()
            val password = binding.etxtPassword.text.toString()
            val rememberMe = binding.recuerdame.isChecked

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginFragmentVM.authWhitFireBase(email, password, auth, requireActivity())
                if (rememberMe) {
                    saveCredentials(email, password)
                } else {
                    clearCredentials()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        // Carga las credenciales guardadas
        loadSavedCredentials()
    }

    private fun saveCredentials(email: String, password: String) {
        sharedPreferences.edit().apply {
            putString("email", email)
            putString("password", password)
            apply()
        }
    }

    private fun clearCredentials() {
        sharedPreferences.edit().apply {
            remove("email")
            remove("password")
            apply()
        }
    }

    private fun loadSavedCredentials() {
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPassword = sharedPreferences.getString("password", "")
        binding.etxtUser.setText(savedEmail)
        binding.etxtPassword.setText(savedPassword)
    }

    private fun initBiometric() {
        biometricManager = BiometricManager.from(requireContext())
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Snackbar.make(binding.imgFinger, "Authentication error: $errString", Snackbar.LENGTH_LONG).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                fetchNicknameAndNavigate()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Snackbar.make(binding.imgFinger, "Authentication failed", Snackbar.LENGTH_LONG).show()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Use your biometric credential to log in")
            .setNegativeButtonText("Cancel")
            .build()

        val checkBiometric = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )

        when (checkBiometric) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                biometricPrompt.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                Snackbar.make(binding.imgFinger, "Biometric authentication is not supported", Snackbar.LENGTH_LONG).show()
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BiometricManager.Authenticators.BIOMETRIC_STRONG or
                                BiometricManager.Authenticators.DEVICE_CREDENTIAL
                    )
                }
                startActivity(enrollIntent)
            }
            else -> {
                Snackbar.make(binding.imgFinger, "Unexpected error in biometric sensor", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun fetchNicknameAndNavigate() {
        val user = auth.currentUser ?: return
        val userId = user.uid

        val firestore = FirebaseFirestore.getInstance()
        val userRef = firestore.collection("users").document(userId)

        userRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val nickname = document.getString("nickname") ?: "Nickname"
                val intent = Intent(requireActivity(), MainActivity::class.java)
                intent.putExtra("USER_NICKNAME", nickname)
                startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "No nickname found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
        }
    }
}
