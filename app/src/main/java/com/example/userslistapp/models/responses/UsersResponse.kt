package com.example.userslistapp.models.responses

import com.example.userslistapp.models.dto.GroupDTO
import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("groups") val groups: List<GroupDTO>?
)