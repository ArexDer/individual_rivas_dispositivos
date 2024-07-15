package com.rivas.diego.proyectorivas.logic.usercase.login

import com.rivas.diego.proyectorivas.data.firebase.FireStoreRepository
import com.rivas.diego.proyectorivas.ui.entities.users.UserLogin
import kotlinx.coroutines.flow.flow

class GetUserByEmailAndPasswordUserCase {
                                    //TIPO DE RESULT DEL DATO
   suspend fun invoke(id:String) = flow<Result<UserLogin>>{
        FireStoreRepository().getUserById(id)
            .onSuccess {
                //Devuelvo uno porque es un usuario xq es unico el ID.
                emit(Result.success(it.first()))
            }
            .onFailure {error->
                emit(Result.failure(error))
            }
    }
}