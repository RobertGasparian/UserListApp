package com.example.userslistapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.ui.fragments.UIState
import com.example.userslistapp.usecases.AddUserUseCase
import com.example.userslistapp.usecases.DeleteUserUseCase
import com.example.userslistapp.usecases.GetAllUsersUseCase
import kotlinx.coroutines.*

abstract class UserListViewModel(app: Application): BaseViewModel<UIState>(app) {
    abstract fun getUsers()
    abstract fun addUser(firstName: String, lastName: String, statusMessage: String)
    abstract fun deleteUser(user: User)
}

class UserListViewModelImpl(
    app: Application,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
): UserListViewModel(app) {

    override fun uiState(): LiveData<UIState> = uiState

    override fun getUsers() {
        uiState.value = UIState.Loading
        val exHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            if (throwable !is CancellationException) {
                uiState.value = UIState.Error("${throwable.message}")
            } else {
                //Cancel case
            }
        }
        viewModelScope.launch(Dispatchers.IO + exHandler) {
            val users = getAllUsersUseCase.getAllUsers()
            withContext(Dispatchers.Main) {
                uiState.value = UIState.Success(users)
            }
        }
    }

    override fun addUser(firstName: String, lastName: String, statusMessage: String) {
        val exHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            if (throwable !is CancellationException) {
                uiState.value = UIState.Error("${throwable.message}")
            } else {
                //Cancel case
            }
        }
        viewModelScope.launch(Dispatchers.IO + exHandler) {
            //TODO: need to be optimized
            addUserUseCase.addUser(firstName, lastName, statusMessage)
            val users = getAllUsersUseCase.getAllUsers()
            withContext(Dispatchers.Main) {
                uiState.value = UIState.Success(users)
            }
        }
    }

    override fun deleteUser(user: User) {
        val exHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            if (throwable !is CancellationException) {
                uiState.value = UIState.Error("${throwable.message}")
            } else {
                //Cancel case
            }
        }
        viewModelScope.launch(Dispatchers.IO + exHandler) {
            //TODO: need to be optimized
            deleteUserUseCase.deleteUser(user)
            val users = getAllUsersUseCase.getAllUsers()
            withContext(Dispatchers.Main) {
                uiState.value = UIState.Success(users)
            }
        }
    }
}