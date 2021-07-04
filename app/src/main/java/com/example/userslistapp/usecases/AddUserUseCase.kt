package com.example.userslistapp.usecases

import com.example.userslistapp.models.appmodels.User

interface AddUserUseCase {
    suspend fun addUser(firstName: String, lastName: String, statusMessage: String)
}

suspend fun AddUserUseCase.addUser(user: User) {
    addUser(user.firstName, user.lastName, user.statusMessage)
}

class AddUserUseCaseImpl: AddUserUseCase {
    override suspend fun addUser(firstName: String, lastName: String, statusMessage: String) {
        TODO("Not yet implemented")
    }

}