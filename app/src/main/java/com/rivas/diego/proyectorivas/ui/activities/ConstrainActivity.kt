package com.rivas.diego.proyectorivas.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.databinding.ActivityConstrainBinding

class ConstrainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConstrainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConstrainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()

    }


    private fun initListeners() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.listarItem -> {

                    true
                }

                R.id.FavItem -> {

                    true
                }

                R.id.NoFavItem -> {

                    true
                }

                else -> false
            }
        }
    }


}