package com.example.userslistapp.repositories

import com.example.userslistapp.database.UserDao
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.mappers.converters.UserConverter
import com.example.userslistapp.networking.ApiService

interface UserRepo {
    suspend fun getAllUsers(): List<User>
    suspend fun addUser(firstName: String, lastName: String, statusMessage: String)
    suspend fun delete(user: User)
}

class UserRepoImpl(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val userConverter: UserConverter
) : UserRepo {
    override suspend fun getAllUsers(): List<User> {
        var userDBMList = userDao.getAllUsers()
        if (userDBMList.isEmpty()) {
            val usersResponse = apiService.getUsers()
            userDBMList = usersResponse.groups?.flatMap {
                it.people ?: emptyList()
            }?.mapNotNull {
                userConverter.dtoToDbm(it)
            } ?: emptyList()
            userDao.insertAll(*userDBMList.toTypedArray())
            return userDBMList.map { userConverter.dbmToModel(it) }
        }
        return userDBMList.map { userConverter.dbmToModel(it) }
    }

    override suspend fun addUser(firstName: String, lastName: String, statusMessage: String) {
        apiService.addUser(firstName, lastName, statusMessage)
        userDao.insertAll(userConverter.modelToDbm(User(firstName, lastName, statusMessage)))
    }

    override suspend fun delete(user: User) {
        apiService.deleteUser(userConverter.modelToDTO(user))
        userDao.delete(userConverter.modelToDbm(user))
    }

}