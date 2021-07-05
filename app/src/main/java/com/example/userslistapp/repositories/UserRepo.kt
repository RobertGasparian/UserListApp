package com.example.userslistapp.repositories

import com.example.userslistapp.database.UserDao
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.mappers.converters.UserConverter
import com.example.userslistapp.networking.ApiService
import kotlinx.coroutines.delay
import okhttp3.Response
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

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
            return userDao.getAllUsers().map { userConverter.dbmToModel(it) }
        }
//        else {
//            //uncomment for testing loading
//            delay(1000)
//        }
        return userDBMList.map { userConverter.dbmToModel(it) }
    }

    override suspend fun addUser(firstName: String, lastName: String, statusMessage: String) {
        apiService.addUser()
        userDao.insertAll(userConverter.modelToDbm(User(firstName, lastName, statusMessage)))
    }

    override suspend fun delete(user: User) {
        apiService.deleteUser()
        userDao.delete(userConverter.modelToDbm(user))
    }

}