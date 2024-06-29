package com.rivas.diego.proyectorivas.ui.core

import android.app.Application
import androidx.room.Room
import com.rivas.diego.proyectorivas.data.local.repository.DataBaseRepository

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        dbConnection =
            Room.databaseBuilder(
                applicationContext,
                DataBaseRepository::class.java,
                "Datos"
            ).build()
    }


    companion object {

        private var dbConnection: DataBaseRepository? = null

        fun getDBConnection(): DataBaseRepository {
            return dbConnection!!
        }
    }
}