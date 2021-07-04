package com.example.userslistapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.ui.fragments.UIState

interface UserListViewModel: UiStateEmitter<UIState> {
    fun getUsers()
    fun addUser(firstName: String, lastName: String, statusMessage: String)
    fun deleteUser(user: User)
}

class UserListViewModelImpl(
    app: Application
): BaseViewModel<UIState>(app), UserListViewModel {

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