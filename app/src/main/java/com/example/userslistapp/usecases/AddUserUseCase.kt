package com.example.userslistapp.usecases

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.repositories.UserRepo

interface AddUserUseCase {
    suspend fun addUser(firstName: String, lastName: String, statusMessage: String)
}

suspend fun AddUserUseCase.addUser(user: User) {
    addUser(user.firstName, user.lastName, user.statusMessage)
}

class AddUserUseCaseImpl(userRepo: UserRepo): UsersUseCase(userRepo), AddUserUseCase {
    override suspend fun addUser(firstName: String, lastName: String, statusMessage: String) {
        userRepo.addUser(firstName, lastName, statusMessage)
    }

}