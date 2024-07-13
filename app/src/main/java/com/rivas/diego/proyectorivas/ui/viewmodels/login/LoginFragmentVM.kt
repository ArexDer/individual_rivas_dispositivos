package com.rivas.diego.proyectorivas.ui.viewmodels.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.rivas.diego.proyectorivas.logic.usercase.login.GetUserWithNameAndPassword
import com.rivas.diego.proyectorivas.ui.activities.MainActivity
import com.rivas.diego.proyectorivas.ui.core.UIStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginFragmentVM:ViewModel() {

    private var auth: FirebaseAuth?= null

    private val context: FragmentActivity?= null

    var uiState = MutableLiveData<UIStates>()
    var idUser =MutableLiveData<String>()




    fun getUserFromDB(name:String, password:String, contex: Context){
        viewModelScope.launch {
            uiState.postValue(UIStates.Loading(true))

            GetUserWithNameAndPassword(contex).invoke(name,password)
                .collect{
                    it.onSuccess {
                        idUser.postValue(it.id)
                    }
                    it.onFailure {
                        uiState.postValue(UIStates.Error(it.message.toString()))
                    }

            }
            delay(3000)
            uiState.postValue(UIStates.Loading(false))
        }
    }



    fun authWhitFireBase(email:String, password:String,auth:FirebaseAuth, context:FragmentActivity){
        viewModelScope.launch {
            uiState.postValue(UIStates.Loading(true))
            auth.signInWithEmailAndPassword(email,password).
                    addOnCompleteListener(context){task->
                        if(task.isSuccessful){
                            uiState.postValue(UIStates.Success(true))
                        }else{
                            Log.w("TAG","Error al entrar con correo", task.exception)
                            uiState.postValue(UIStates.Error(task.exception?.message.toString()))
                        }
                    }
            delay(500)
            uiState.postValue(UIStates.Loading(false))
        }
    }
}