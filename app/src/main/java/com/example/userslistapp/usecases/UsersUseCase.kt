package com.example.userslistapp.usecases

import com.example.userslistapp.repositories.UserRepo

abstract class UsersUseCase(protected val userRepo: UserRepo)