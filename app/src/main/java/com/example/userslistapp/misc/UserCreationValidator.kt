package com.example.userslistapp.misc

import com.example.userslistapp.models.appmodels.User

interface UserCreationValidator {
    fun validate(user: User): ValidationStatus

    sealed class ValidationStatus {
        data class Valid(val user: User) : ValidationStatus()
        data class Invalid(
            val isFirstNameValid: Boolean,
            val isLastNameValid: Boolean,
            val isStatusMessageValid: Boolean,
        ) : ValidationStatus()
    }
}

object UserCreationValidatorImpl : UserCreationValidator {
    override fun validate(user: User): UserCreationValidator.ValidationStatus {
        val isFirstNameValid = user.firstName.isNotEmpty()
        val isLastNameValid = user.lastName.isNotEmpty()
        val isStatusMessageValid = true
        return if (isFirstNameValid && isLastNameValid && isStatusMessageValid) {
            UserCreationValidator.ValidationStatus.Valid(user)
        } else UserCreationValidator.ValidationStatus.Invalid(
            isFirstNameValid,
            isLastNameValid,
            isStatusMessageValid
        )
    }
}

