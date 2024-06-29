package com.rivas.diego.proyectorivas.logic.usercase.login

import com.rivas.diego.proyectorivas.data.local.database.entities.UsersDB
import com.rivas.diego.proyectorivas.data.local.repository.DataBaseRepository


class SaveUserpasswordUserCase(private val conn: DataBaseRepository) {

    suspend operator fun invoke(
        user: String,
        password: String
    ): Boolean {

        return try {
            val us = conn.getUserDao().saveUser(
                listOf(
                    UsersDB(name = user, password = password)
                )
            )
            true
        } catch (ex: Exception) {
            false
        }
    }
}