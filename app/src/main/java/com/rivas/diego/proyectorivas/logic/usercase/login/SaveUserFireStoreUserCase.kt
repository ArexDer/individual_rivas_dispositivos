package com.rivas.diego.proyectorivas.logic.usercase.login


import android.annotation.SuppressLint
import com.rivas.diego.proyectorivas.data.firebase.FireStoreRepository
import com.rivas.diego.proyectorivas.ui.entities.users.UserLogin

import kotlinx.coroutines.flow.flow


class SaveUserFireStoreUserCase {

    @SuppressLint("SuspiciousIndentation")
    suspend fun invoke(user : UserLogin)= flow{
        val  x= FireStoreRepository().saveUserLogin(user)
            x.onSuccess {
                emit(it)
            }
            x.onFailure {
                emit(false)

            }
    }
}