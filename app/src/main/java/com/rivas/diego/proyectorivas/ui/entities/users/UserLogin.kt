package com.rivas.diego.proyectorivas.ui.entities.users

//BUSCAR Y VER SI EL OBJETO ES SERIALIZABLE..
//BLIBIOTEC PARA QEUSE PUEDA SERIALIZAR

data class UserLogin (val uuid:String,
                        val name:String,
                       val lastName:String){
    // Constructor sin argumentos requerido por Firestore para la deserialización
    constructor() : this("", "", "")
}