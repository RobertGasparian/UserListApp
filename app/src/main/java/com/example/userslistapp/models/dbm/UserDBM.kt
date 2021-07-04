package com.example.userslistapp.models.dbm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDBM(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "firstName") val firstName: String,
    @ColumnInfo(name = "lastName") val lastName: String,
    @ColumnInfo(name = "statusMessage") val statusMessage: String,
    @ColumnInfo(name = "statusIcon") val statusIcon: String? = null,
)