package com.example.userslistapp.models.dto

import com.google.gson.annotations.SerializedName

data class PersonDTO(
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("statusIcon") val statusIcon: String?,
    @SerializedName("statusMessage") val statusMessage: String?,
)