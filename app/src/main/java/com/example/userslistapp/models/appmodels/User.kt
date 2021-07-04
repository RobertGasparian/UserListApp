package com.example.userslistapp.models.appmodels

import java.io.Serializable

data class User(
    val firstName: String,
    val lastName: String,
    val statusMessage: String,
): Serializable