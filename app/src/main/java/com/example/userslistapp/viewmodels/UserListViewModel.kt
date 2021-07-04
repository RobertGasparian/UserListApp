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
import java.lang.Exception

abstract class UserListViewModel(app: Application) : BaseViewModel<UIState>(app) {
    abstract fun getUsers()
    abstract fun addUser(firstName: String, lastName: String, statusMessage: String)
    abstract fun deleteUser(user: User)
    abstract fun tryToDelete(user: User)
}

class UserListViewModelImpl(
    app: Application,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
) : UserListViewModel(app) {

    override fun uiState(): LiveData<UIState> = uiState

    override fun getUsers() {
        uiState.value = UIState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val users = getAllUsersUseCase.getAllUsers()
                withContext(Dispatchers.Main) {
                    uiState.value = UIState.Success(users)
                }
            } catch (ex: Exception) {
                if (ex !is CancellationException) {
                    withContext(Dispatchers.Main) {
                        uiState.value = UIState.Error(ex.message)
                    }
                } else {
                    //Cancel case
                }
            }
        }
    }

    override fun addUser(firstName: String, lastName: String, statusMessage: String) {
        uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            //TODO: need to be optimized
            try {
                addUserUseCase.addUser(firstName, lastName, statusMessage)
                val users = getAllUsersUseCase.getAllUsers()
                withContext(Dispatchers.Main) {
                    uiState.value = UIState.Success(users)
                }
            } catch (ex: Exception) {
                if (ex !is CancellationException) {
                    withContext(Dispatchers.Main) {
                        uiState.value = UIState.Error(ex.message)
                    }
                } else {
                    //Cancel case
                }
            }
        }
    }

    override fun deleteUser(user: User) {
        uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            //TODO: need to be optimized
            try {
                deleteUserUseCase.deleteUser(user)
                val users = getAllUsersUseCase.getAllUsers()
                withContext(Dispatchers.Main) {
                    uiState.value = UIState.Success(users)
                }
            } catch (ex: Exception) {
                if (ex !is CancellationException) {
                    withContext(Dispatchers.Main) {
                        uiState.value = UIState.Error(ex.message)
                    }
                } else {
                    //Cancel case
                }
            }
        }
    }

    override fun tryToDelete(user: User) {
        uiState.value = UIState.DeleteDialog(user)
    }
}