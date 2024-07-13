package com.rivas.diego.proyectorivas.ui.fragments.login

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.data.local.repository.DataBaseRepository
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

    //NUEVAS...
    private lateinit var con: DataBaseRepository
    private lateinit var managerUIStates: ManageUIStates
    private val loginFragmentVM:LoginFragmentVM by viewModels()

    //FIREBASE
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentLoginBinding.bind(inflater.inflate(R.layout.fragment_login, container, false))
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      //  initVariables()
        initListeners()

        //NUEVAS
        initiObservers()
        initVariables()
    }

    private fun initVariables() {

        // Initialize Firebase Auth
        auth= Firebase.auth


        managerUIStates= ManageUIStates(requireActivity(),binding.lytLoading.mainLayout)
        loginFragmentVM.initGlobalVars(auth,requireActivity())

        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            startActivity(Intent(requireActivity(),MainActivity::class.java))
//            //Si esta Logeado va directamente a la Main Activity, caso contrario se queda aqui
//        }

    }

    private fun initiObservers() {

        loginFragmentVM.uiState.observe(viewLifecycleOwner){
            states->managerUIStates.invoke(states)
        }

        loginFragmentVM.idUser.observe(viewLifecycleOwner) { id ->

            startActivity(
                Intent(
                    requireActivity(),
                    MainActivity::class.java
                )
            )
            requireActivity().finish()
        }

    }

    private fun initBiometric() {
        val checkBiometric = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.DEVICE_CREDENTIAL
                    or
                    BiometricManager.Authenticators.BIOMETRIC_STRONG
        )

        when (checkBiometric) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                biometricPrompt.authenticate(promptInfo)
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {}
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {}
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BiometricManager.Authenticators.BIOMETRIC_STRONG
                                or
                                BiometricManager.Authenticators.DEVICE_CREDENTIAL
                    )
                }
                startActivity(enrollIntent)
            }

            else -> {
                Snackbar.make(
                    binding.imgFinger, "Ocurrio un error inesperado en el sensor",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }



/*
    private fun initVariables() {
        biometricManager = BiometricManager.from(requireActivity())
        executor = ContextCompat.getMainExecutor(requireActivity())
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("My application")
            .setSubtitle("Ingrese su huella digital")
            .setNegativeButtonText("Cancelar")
            .build()

        biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {


                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)

                    val x = Intent(requireActivity(), ConstrainActivity::class.java)
                    startActivity(x)
                }

            }
        )
    }

 */

    private fun initListeners() {

        binding.btnSigIn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
           // findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        binding.imgFinger.setOnClickListener {
            initBiometric()
        }

        //Aqui hacerle el cambio para ver los datos de el usuario., hasta borrarlol.

        binding.btnLogin.setOnClickListener {

            auth.signInWithEmailAndPassword(

        }
        /*
        binding.btnLogin.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_recoveyFragment)

l
        }

         */

        /*
        binding.btnLogin.setOnClickListener {

            val loginUserCase = LoginUserpasswordUserCase(
                MyApplication.getDBConnection()
            )

            lifecycleScope.launch(Dispatchers.Main) {

                binding.lytLoading.root.visibility = View.VISIBLE

                val result = withContext(Dispatchers.IO) {
                    loginUserCase(
                        binding.etxtUser.text.toString(),
                        binding.etxtPassword.text.toString()
                    )
                }
                result.onSuccess { user ->
                    val intentToConstarintAct = Intent(
                        requireActivity(),
                        ConstrainActivity::class.java
                    )
                    startActivity(intentToConstarintAct)
                    binding.lytLoading.root.visibility = View.GONE
                }

                result.onFailure {
                    Toast.makeText(
                        requireContext(),
                        it.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.lytLoading.root.visibility = View.GONE
        }

         */


    }

    private fun LoginLocal() {
        val username = binding.etxtUser.text.toString()
        val password = binding.etxtPassword.text.toString()

        lifecycleScope.launch {
            loginFragmentVM.getUserFromDB(username, password, requireContext())
        }
    }
}