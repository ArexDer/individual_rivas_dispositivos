package com.rivas.diego.proyectorivas.ui.viewmodels.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivas.diego.proyectorivas.logic.usercase.login.CreateUserWithNameAndPassword
import com.rivas.diego.proyectorivas.ui.core.UIStates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class RegisterFragmentVM: ViewModel() {


    //AQUI PUEDO MANEJAR OCN UN SOLO ESTADO.
    var uiState= MutableLiveData<UIStates>()

     fun saveUser(name:String, password:String, context: Context){

       viewModelScope.launch {

           //Este estado esta cargando antes.
           uiState.postValue(UIStates.Loading(true))
           CreateUserWithNameAndPassword(context).invoke(name,password)
               .collect{
                   it.onSuccess{
                       uiState.postValue(UIStates.Success(it))
                   }
                   it.onFailure{
                        uiState.postValue(UIStates.Error(it.message.toString()))
                   }
               }
           //Cuando ya esta cargado me cambia a False.
           delay(500)
           uiState.postValue(UIStates.Loading(false))
       }
    }

}


//SINGLE CLASS

   //Una clase normal, que tiene clase y objetos que me permiten ocmprobar, sino
   //Son Exaustivo -> Si o si se tiene que usar en todo lo que tenga alli
