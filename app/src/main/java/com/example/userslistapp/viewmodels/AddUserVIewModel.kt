package com.example.userslistapp.viewmodels

import com.example.userslistapp.misc.UserCreationValidator
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.ui.fragments.dialogs.UIState

abstract class AddUserVIewModel: BaseViewModel<UIState>() {
    abstract fun tryToAdd(firstName: String, lastName: String, statusMessage: String)
    abstract fun firstNameChanged(text: String)
    abstract fun lastNameChanged(text: String)
}

class AddUserVIewModelImpl(
    private val validator: UserCreationValidator
): AddUserVIewModel() {

    override fun tryToAdd(firstName: String, lastName: String, statusMessage: String) {
        val user = User(
            firstName,
            lastName,
            statusMessage,
        )
        when (val status = validator.validate(user)) {
            is UserCreationValidator.ValidationStatus.Valid -> {
                uiState.value = UIState.ValidData(status.user)
            }
            is UserCreationValidator.ValidationStatus.Invalid -> {
                uiState.value = UIState.InvalidData(status.isFirstNameValid, status.isLastNameValid)
            }
        }
    }

    override fun firstNameChanged(text: String) {
        uiState.value = UIState.HideFirstNameError
    }

    override fun lastNameChanged(text: String) {
        uiState.value = UIState.HideLastNameError
    }
}