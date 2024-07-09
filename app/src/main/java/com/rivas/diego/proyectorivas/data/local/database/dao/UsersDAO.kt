package com.rivas.diego.proyectorivas.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rivas.diego.proyectorivas.data.local.database.entities.UsersDB


@Dao
interface UsersDAO {

    @Query("select * from users")
    fun getAllUsers(): List<UsersDB>

    @Query("select * from users where id_user = :id")
    fun getUserById(id: Int): UsersDB

    @Query("select * from users where name_user = :us AND password_user = :pas")
    fun getUserByPassAndUser(us: String, pas : String): UsersDB

    @Insert
    fun saveUser(user: List<UsersDB>)

    @Delete
    fun deleteUser(user: UsersDB)

}