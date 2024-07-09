package com.rivas.diego.proyectorivas.data.local.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rivas.diego.proyectorivas.data.local.database.dao.UsersDAO
import com.rivas.diego.proyectorivas.data.local.database.entities.UsersDB
import kotlinx.coroutines.InternalCoroutinesApi

@Database(
    entities = [UsersDB::class],
    version = 1
)
abstract class DataBaseRepository : RoomDatabase() {
    abstract fun getUserDao(): UsersDAO


    // Es el analogo o el igual como FINAL en JAVA.
    // FINAL --> INMUTABLE COMO EL PATRON SINGLETON.
    companion object {

    //inmutable
    //Siempre esten visibles desde otros subprocesosos
        @Volatile //de JVM -> Lecturas y escrituras rapidas.
        private var dbConnection: DataBaseRepository? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDBConnection(context: Context): DataBaseRepository {
            return  if(dbConnection==null){
                kotlinx.coroutines.internal.synchronized(this){
                    val INSTANCE =
                        Room.databaseBuilder(
                            context,
                            DataBaseRepository::class.java,
                            "Datos"
                        ).build()
                    dbConnection=INSTANCE
                    dbConnection!!
                }
            } else{
                 dbConnection!!
            }

        }
    }
}

//VAMOS A OCUPAR LA SINCRONIZACION PARA QEU ESTO SE BLOQUEE Y SEA UNA SOLA INSTANCIA
//EN UN MOMENTO ESPECIFICO --> CON UNA CORRUTINA.
//LAS CORRUTINAS SON --> HILOS SEPARADOS.
//AQUI QUEREMOS QUE LOS DOS LLEGUEN A UN OBJETO INMUTABLE
