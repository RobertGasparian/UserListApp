package com.example.userslistapp.usecases

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.repositories.UserRepo
import java.lang.Exception

interface DeleteUserUseCase {
    suspend fun deleteUser(user: User)
}

class DeleteUserUseCaseImpl(userRepo: UserRepo): UsersUseCase(userRepo), DeleteUserUseCase {
    override suspend fun deleteUser(user: User) {
        userRepo.delete(user)
    }
}