package com.rivas.diego.proyectorivas.data.firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.rivas.diego.proyectorivas.ui.entities.users.UserLogin
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await


class FireStoreRepository {

    //Inicializo la variable cuando creo este repositorio
    private  var  db:FirebaseFirestore = Firebase.firestore

    /*
    El  kotlin.runCatching ya me devuelve un resultado. rsult de un tipo
    BLOQUE DE EJECUCION
     */
    suspend fun saveUserLogin(user:UserLogin)= kotlin.runCatching{
        val id = db.collection("users")
            .add(user)
            .await() //Con esto se conveirte en una tarea sincronica
            .id //Me devuelve el string id del objeto
        return@runCatching  true  // return@runCatching xq es la respuesta de el bloque.

        //Si no se ejecuta bien, se me ejecuta una tarea interna de ERROR.

    }

    suspend fun getUserById(id: String) = kotlin.runCatching{
        var items = arrayListOf<UserLogin>()
        db.collection("users") //a mi nombre de la consulta en FIRESTORE de databse
            .whereEqualTo("uuid",id)
            .get()
            .await()
            .forEach{
                //Esto es una lista.
                items.add(it.toObject<UserLogin>())

            }
        return@runCatching items

    }
}