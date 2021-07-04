package com.example.userslistapp.networking

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dto.PersonDTO
import com.example.userslistapp.models.responses.AddUserResponse
import com.example.userslistapp.models.responses.DeleteUserResponse
import com.example.userslistapp.models.responses.UsersResponse
import retrofit2.http.GET

interface ApiService {
    @GET("32EQvG6T8eYFiA2/download")
    suspend fun getUsers(): UsersResponse

    @GET("ILsnzqADc0o3NGr/download")
    suspend fun addUser(firstName: String, lastName: String, statusMessage: String): AddUserResponse

    @GET("uvbyRgwuSZaZ2IV/download")
    suspend fun deleteUser(user: PersonDTO): DeleteUserResponse
}