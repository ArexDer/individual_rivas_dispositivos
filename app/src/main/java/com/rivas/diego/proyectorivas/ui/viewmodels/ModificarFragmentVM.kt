package com.rivas.diego.proyectorivas.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivas.diego.proyectorivas.logic.usercase.contador.GetDataAsync
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ModificarFragmentVM: ViewModel() {

    val getDataAsync = GetDataAsync()

    var final =0


    var cLive = MutableLiveData<Int>()  //ESTA VARIABLE TIENE QEU SER PUBLICA y esucharla en el FRAGMENT
    //var cLive= getDataAsync.invoke().asLiveData()   ---------> SERIA MAS CORTO y
    //fun funcionContar(){} VA VACIOP

    //CADA VEZ QEU SE CAMBIA LE INFORMA A LA UI....
    //NO SE PASAN LOS DATOS IGUALANDO PORQUE ESTE ES UN CONTEDOR
    fun funcionContar(){
        //Una corrutina dentro de un VM
        viewModelScope.launch(Dispatchers.IO) {
            //c+=1
            //cLive va a guardar lo qeu tenia C.
            val x= getDataAsync.invoke()
            //CASI COMO UN FLUJO, SOLO GUARDA EL ULTIMO VALOR.
            x.collect{
                cLive.postValue(it)
            }
        }
    }

    fun funcionParar(){
        viewModelScope.launch(Dispatchers.IO) {

            val y= getDataAsync.invokeStop()




        }


    }

}