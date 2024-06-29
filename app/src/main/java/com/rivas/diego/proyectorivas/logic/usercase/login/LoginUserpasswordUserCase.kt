package com.rivas.diego.proyectorivas.logic.usercase.login

import com.rivas.diego.proyectorivas.data.local.database.entities.UsersDB
import com.rivas.diego.proyectorivas.data.local.repository.DataBaseRepository

class LoginUserpasswordUserCase(private val conn: DataBaseRepository) {

    suspend operator fun invoke(
        user: String,
        password: String
    ): Result<UsersDB> {

        return try {
            val us = conn.getUserDao().getUserByPassAndUser(user, password)
            Result.success(us)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}