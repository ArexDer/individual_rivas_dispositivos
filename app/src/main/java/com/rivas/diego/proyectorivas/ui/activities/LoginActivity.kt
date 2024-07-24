package com.rivas.diego.proyectorivas.ui.activities

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rivas.diego.proyectorivas.R

import com.rivas.diego.proyectorivas.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splash = installSplashScreen()

        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splash.setKeepOnScreenCondition{
            false
        }
        Log.d("LoginActivity", "onCreate: finished")
    }
}
