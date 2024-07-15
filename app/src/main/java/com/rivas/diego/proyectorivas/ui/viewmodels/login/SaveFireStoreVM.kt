package com.rivas.diego.proyectorivas.ui.viewmodels.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivas.diego.proyectorivas.logic.usercase.login.GetUserByEmailAndPasswordUserCase
import com.rivas.diego.proyectorivas.logic.usercase.login.SaveUserFireStoreUserCase
import com.rivas.diego.proyectorivas.ui.core.UIStates
import com.rivas.diego.proyectorivas.ui.entities.users.UserLogin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SaveFireStoreVM: ViewModel() {


    // _   El guion bajo lo ahce privada a mi variable:    _userUI
     val userUI get() = _userUI  //Esta es inmutable y le hace referencai la de abajo
    private var _userUI= MutableLiveData<UIStates>() //Esta variable es local


    val userLogin get() = _userLogin
    private var _userLogin= MutableLiveData<UserLogin>()


    //Con esto nadie la modifica

    fun saveUserFireStore(user:UserLogin) {
        viewModelScope.launch {
            SaveUserFireStoreUserCase().invoke(user)

                .collect {
                    if (it) {
                        _userUI.postValue(UIStates.Success(true))
                    } else {
                        _userUI.postValue(
                            UIStates.Error(
                                "Ocurrio un Error al Generar" +
                                        " la PETICION, INTENTELO MAS TARDE"
                            )
                        )
                    }


                }
            //La envio con Corrutina para no hacer suspendida la
            //SaveUserFireStoreUserCase().invoke(user)
            //Por eso lo meto todo dentro del ViewModel
        }
    }


        fun getUserByIdFireStore(id:String){
            _userUI.postValue(UIStates.Loading(true))
            viewModelScope.launch {

                GetUserByEmailAndPasswordUserCase().invoke(id)
                    .collect{resp->
                        resp.onSuccess {
                            _userLogin.postValue(it)

                        }
                        resp.onFailure {
                            _userUI.postValue(UIStates.Error(it.message.toString()))
                        }

                    }
                delay(500)
                _userUI.postValue(UIStates.Loading(false))

            }

        }

    }
/*
Custom Exception Manager... -> Mnadmos a una clase para que le traduzca al usuaior
que  esta psando ....
EL mensaje qeu yo tengo pues  lo hagomas entendible para el usuario...
 */

}