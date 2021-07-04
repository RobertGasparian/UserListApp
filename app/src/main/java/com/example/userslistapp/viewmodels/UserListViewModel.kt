package com.example.userslistapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.ui.fragments.UIState

abstract class UserListViewModel(app: Application): BaseViewModel<UIState>(app) {
    abstract fun getUsers()
    abstract fun addUser(firstName: String, lastName: String, statusMessage: String)
    abstract fun deleteUser(user: User)
}

class UserListViewModelImpl(
    app: Application
): UserListViewModel(app) {

    override fun uiState(): LiveData<UIState> = uiState

    override fun getUsers() {
        TODO("Not yet implemented")
    }

    override fun addUser(firstName: String, lastName: String, statusMessage: String) {
        TODO("Not yet implemented")
    }

    override fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }
}