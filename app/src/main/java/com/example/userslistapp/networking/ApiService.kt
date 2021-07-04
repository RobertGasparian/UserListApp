package com.example.userslistapp.networking

import com.example.userslistapp.models.responses.UsersResponse
import retrofit2.http.GET

interface ApiService {
    @GET("32EQvG6T8eYFiA2/download")
    suspend fun getUsers(): UsersResponse
}