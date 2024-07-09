package com.rivas.diego.proyectorivas.logic.usercase.contador

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow


class GetDataAsync {

    private var c = 1


    fun invoke()= flow{
        //LIVE DATA NO FUNCIOAN EN LA LOGICA
        while (true){
            //usabndo CORRUTINAS
            delay(2000) //CADA 5 SEGUNDOS SE SUMA UNO MAS
            c += 1
            //ya no tenemos un retorno, sino que llenamos nuestro FLUJO
            emit(c)

        }
    }

    fun invokeStop(){

        c=0


    }
}