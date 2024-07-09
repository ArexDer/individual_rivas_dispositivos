package com.rivas.diego.proyectorivas.ui.core

//Clase sealed
sealed class UIStates {

    //CREO TODAS LAS CLASES QUE NECESITO PARA EL MANEJO.

    class Success(val condition:Boolean):UIStates()
    class Error(val message:String):UIStates()
    class  Loading( val isLoading: Boolean):UIStates() //Si esta true PROGRES BAR, FALLS CONTRARIO
}