package com.example.userslistapp.usecases

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.repositories.UserRepo

interface GetAllUsersUseCase {
    suspend fun getAllUsers(): List<User>
}

class GetAllUsersUseCaseImpl(userRepo: UserRepo): UsersUseCase(userRepo), GetAllUsersUseCase {
    override suspend fun getAllUsers() = userRepo.getAllUsers()
}