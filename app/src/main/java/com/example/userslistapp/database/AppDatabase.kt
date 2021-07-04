package com.example.userslistapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.userslistapp.models.dbm.UserDBM

@Database(entities = [UserDBM::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}