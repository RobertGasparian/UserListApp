package com.example.userslistapp.models.dbm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDBM(
    @ColumnInfo(name = "firstName") val firstName: String,
    @ColumnInfo(name = "lastName") val lastName: String,
    @ColumnInfo(name = "statusMessage") val statusMessage: String,
    @PrimaryKey val uid: String = firstName + lastName + statusMessage,
)