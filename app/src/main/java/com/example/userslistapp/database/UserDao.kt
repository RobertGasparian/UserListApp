package com.example.userslistapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.userslistapp.models.dbm.UserDBM

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UserDBM>

    @Query("SELECT * FROM users WHERE firstName LIKE :firstName AND lastName LIKE :lastName LIMIT 1")
    suspend fun getUserBy(firstName: String, lastName: String): UserDBM

    @Insert
    suspend fun insertAll(vararg userDBM: UserDBM)

    @Delete
    suspend fun delete(user: UserDBM)
}