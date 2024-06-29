package com.rivas.diego.proyectorivas.ui.viewmodels.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivas.diego.proyectorivas.logic.usercase.login.CreateUserWithNameAndPassword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class RegisterFragmentVM: ViewModel() {

    var userSaved =MutableLiveData<Boolean>(
    )
    suspend fun saveUser(name:String, password:String, context: Context){

            val x =CreateUserWithNameAndPassword(context).invoke(name,password)
        x.apply {
            launchIn(scope = viewModelScope)
            collect{
                    userSaved.postValue(it)
            }
        }
            userSaved.postValue(true)
    }

}