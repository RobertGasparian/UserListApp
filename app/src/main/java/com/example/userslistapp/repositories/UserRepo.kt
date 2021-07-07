package com.example.userslistapp.repositories

import android.accounts.NetworkErrorException
import com.example.userslistapp.database.UserDao
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.mappers.converters.UserConverter
import com.example.userslistapp.networking.ApiService
import com.example.userslistapp.networking.NetworkService

interface UserRepo {
    suspend fun getAllUsers(): List<User>
    suspend fun addUser(firstName: String, lastName: String, statusMessage: String)
    suspend fun delete(user: User)
}

class UserRepoImpl(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val userConverter: UserConverter,
    private val networkService: NetworkService
) : UserRepo {

    /**
     * In this specific case the logic of repository is a little bit distorted
     * because we are are fetching data only at first time or when all users are deleted
     * (otherwise our Add/Delete transactions cannot be saved),
     * but in real case we must fetch data more frequently and refresh database. Also the
     * single source of truth is DB so we must always return data from database and refresh it
     * when new data arrives (preferably with subscription mechanism like LiveData, Observable or Flow),
     * and if I would have time, I must implement it.So...
     * TODO: implement subscription mechanism
     */
    override suspend fun getAllUsers(): List<User> {
        var userDBMList = userDao.getAllUsers()
        if (userDBMList.isEmpty()) {
            if (networkService.isConnected()) {
                val usersResponse = apiService.getUsers()
                userDBMList = usersResponse.groups?.flatMap {
                    it.people ?: emptyList()
                }?.mapNotNull {
                    userConverter.dtoToDbm(it)
                } ?: emptyList()
                userDao.insertAll(*userDBMList.toTypedArray())
                return userDao.getAllUsers().map { userConverter.dbmToModel(it) }
            } else throw NetworkErrorException("No network connection!")
        }
//        else {
//            //uncomment for testing loading
//            delay(1000)
//        }
        return userDBMList.map { userConverter.dbmToModel(it) }
    }

    override suspend fun addUser(firstName: String, lastName: String, statusMessage: String) {
        if (networkService.isConnected()) {
            apiService.addUser()
            userDao.insertAll(userConverter.modelToDbm(User(firstName, lastName, statusMessage)))
        } else throw NetworkErrorException("No network connection!")
    }

    override suspend fun delete(user: User) {
        if (networkService.isConnected()) {
            apiService.deleteUser()
            userDao.delete(userConverter.modelToDbm(user))
        } else throw NetworkErrorException("No network connection!")
    }

}