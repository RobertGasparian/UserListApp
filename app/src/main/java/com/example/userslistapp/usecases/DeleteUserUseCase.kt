package com.example.userslistapp.usecases

import com.example.userslistapp.models.appmodels.User

interface DeleteUserUseCase {
    suspend fun deleteUser(user: User)
}

class DeleteUserUseCaseImpl: DeleteUserUseCase {
    override suspend fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }

}