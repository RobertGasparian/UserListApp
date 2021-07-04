package com.example.userslistapp.usecases

import com.example.userslistapp.models.appmodels.User

interface GetAllUsersUseCase {
    suspend fun getAllUsers(): List<User>
}

class GetAllUsersUseCaseImpl: GetAllUsersUseCase {
    override suspend fun getAllUsers(): List<User> {
        TODO("Not yet implemented")
    }
}