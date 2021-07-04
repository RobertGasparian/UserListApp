package com.example.userslistapp.models.dto

import com.google.gson.annotations.SerializedName

data class PersonDTO(
    @SerializedName("firstName") val firstName: String? = null,
    @SerializedName("lastName") val lastName: String? = null,
    @SerializedName("statusIcon") val statusIcon: String? = null,
    @SerializedName("statusMessage") val statusMessage: String? = null,
)