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

abstract class UserListViewModel : BaseViewModel<UIState>() {
    abstract fun getUsers()
    abstract fun tryToAdd()
    abstract fun addUser(firstName: String, lastName: String, statusMessage: String)
    abstract fun cancelAddAction()
    abstract fun tryToDelete(user: User)
    abstract fun cancelDeleteAction()
    abstract fun deleteUser(user: User)
}

class UserListViewModelImpl(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserListViewModel() {

    override fun uiState(): LiveData<UIState> = uiState

    override fun getUsers() {
        uiState.value = UIState.Loading

        viewModelScope.launch(ioDispatcher) {
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

    override fun tryToAdd() {
        uiState.value = UIState.AddUserDialog
    }

    override fun addUser(firstName: String, lastName: String, statusMessage: String) {
        uiState.value = UIState.Loading
        viewModelScope.launch(ioDispatcher) {
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
                    //TODO: need to handle cancel case
                }
            }
        }
    }

    override fun cancelAddAction() {
        // do nothing
    }

    override fun deleteUser(user: User) {
        uiState.value = UIState.Loading
        viewModelScope.launch(ioDispatcher) {
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
                    //TODO: need to handle cancel case
                }
            }
        }
    }

    override fun cancelDeleteAction() {
        // do nothing
    }

    override fun tryToDelete(user: User) {
        uiState.value = UIState.DeleteDialog(user)
    }
}