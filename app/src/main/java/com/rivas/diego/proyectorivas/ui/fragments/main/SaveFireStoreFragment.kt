package com.rivas.diego.proyectorivas.ui.fragments.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase


import com.rivas.diego.proyectorivas.R
import com.rivas.diego.proyectorivas.databinding.FragmentSaveFireStoreBinding
import com.rivas.diego.proyectorivas.ui.entities.users.UserLogin



class SaveFireStoreFragment : Fragment() {

    private lateinit var binding: FragmentSaveFireStoreBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSaveFireStoreBinding.bind(inflater.inflate(R.layout.fragment_save_fire_store
        ,container,false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         db= Firebase.firestore
        auth=Firebase.auth

        initListeners()
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener{
            val user = UserLogin(
                auth.currentUser!!.uid, //Con esto anexo el email con los demas dato del usuario.
                binding.Name.text.toString(),
                binding.LastName.text.toString()
            )

// Add a new document with a generated ID
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }

        binding.btnGet.setOnClickListener{
            db.collection("users")
                .whereEqualTo("uuid",auth.currentUser!!.uid)//Me devolveria un valor igual al anterior
                .get()
                .addOnSuccessListener { result ->
                    result.forEach{
                      val s=  it.toObject<UserLogin>() //JSON  de FIREBASE me lo pasa a DATA Class
                        binding.txtData.text=s.name  //NUEVA FORMA
                        //binding.txtData.text=it.get("name").toString() //Con el WHERE  ANTIGUA FORMA
                    }
                    /*+
                    for (document in result) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                    }
                    *Cuando euso quitandop el Where
                     */
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }
        }
    }


}