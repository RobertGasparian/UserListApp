package com.example.userslistapp.models.dto

import com.google.gson.annotations.SerializedName

data class GroupDTO(
    @SerializedName("groupName") val groupName: String?,
    @SerializedName("people") val people: List<PersonDTO>?,
)