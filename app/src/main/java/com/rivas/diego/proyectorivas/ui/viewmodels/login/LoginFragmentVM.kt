package com.rivas.diego.proyectorivas.ui.viewmodels.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivas.diego.proyectorivas.logic.usercase.login.GetUserWithNameAndPassword
import com.rivas.diego.proyectorivas.ui.core.UIStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginFragmentVM:ViewModel() {


    var uiState = MutableLiveData<UIStates>()
    var idUser =MutableLiveData<Int>()

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
}