package com.rivas.diego.proyectorivas.logic.usercase.login

import com.rivas.diego.proyectorivas.data.firebase.FireStoreRepository
import com.rivas.diego.proyectorivas.ui.entities.users.UserLogin
import kotlinx.coroutines.flow.flow

class GetUserByEmailAndPasswordUserCase {

   suspend fun invoke(id:String) = flow<Result<UserLogin>>{
        FireStoreRepository().getUserById(id)
            .onSuccess {

                emit(Result.success(it.first()))
            }
            .onFailure {error->
                emit(Result.failure(error))
            }
    }
}