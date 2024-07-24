package com.rivas.diego.proyectorivas.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.databinding.ActivityMainBinding
import com.rivas.diego.proyectorivas.ui.fragments.login.LoginFragment

import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog

import androidx.navigation.fragment.NavHostFragment

import com.rivas.diego.proyectorivas.ui.viewmodels.main.ListarMovieInfoVM

class MainActivity : AppCompatActivity() {

    private val listarMovieInfoVM: ListarMovieInfoVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.containerFragments) as? NavHostFragment
        val fragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull()

        if (fragment is LoginFragment) {
            super.onBackPressed()
        } else {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar Sesión")
            .setMessage("¿Quieres cerrar sesión?")
            .setPositiveButton("Sí") { dialog, _ ->
                dialog.dismiss()
                logout()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun logout() {
        // Implementar el método de cierre de sesión aquí
        // Por ejemplo, limpiar las credenciales y volver al fragmento de login
        // Puedes usar SharedPreferences para borrar los datos
        val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        // Volver al fragmento de login
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragments, LoginFragment())
            .commit()
    }
}