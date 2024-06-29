package com.rivas.diego.proyectorivas.data.local.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rivas.diego.proyectorivas.data.local.database.dao.UsersDAO
import com.rivas.diego.proyectorivas.data.local.database.entities.UsersDB

@Database(
    entities = [UsersDB::class],
    version = 1
)
abstract class DataBaseRepository : RoomDatabase() {
    abstract fun getUserDao(): UsersDAO
}