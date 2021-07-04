package com.example.userslistapp.database

import androidx.room.*
import com.example.userslistapp.models.dbm.UserDBM

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserDBM>

    @Query("SELECT * FROM users WHERE firstName LIKE :firstName AND lastName LIKE :lastName LIMIT 1")
    suspend fun getUserBy(firstName: String, lastName: String): UserDBM

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg userDBM: UserDBM)

    @Delete
    suspend fun delete(user: UserDBM)
}