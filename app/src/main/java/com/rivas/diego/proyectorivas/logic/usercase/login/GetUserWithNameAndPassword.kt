package com.rivas.diego.proyectorivas.logic.usercase.login

import android.content.Context
import com.rivas.diego.proyectorivas.data.local.repository.DataBaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetUserWithNameAndPassword(private val context: Context) {

    fun invoke (name:String, password:String) = flow {
        kotlinx.coroutines.delay(2000)
        val us =DataBaseRepository
            .getDBConnection(context)
            .getUserDao()
            .getUserByPassAndUser(name,password)
        if(us != null ){
            emit(Result.success(us))
        } else{
            emit(Result.failure(Exception("No exisite relacion en los valores"+
               "Revise e intene nuevamente")))
        }
    }.catch { emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}