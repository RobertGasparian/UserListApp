package com.example.userslistapp.misc

import com.example.userslistapp.utils.CORRECT_USER
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

class UserCreationValidatorTest {

    // region Constants-----------------------------------------------------------------------------
    companion object {
        const val EMPTY_FIRST_NAME = ""
        const val EMPTY_LAST_NAME = ""
        const val EMPTY_STATUS_MESSAGE = ""
    }
    // endregion Constants--------------------------------------------------------------------------

    // region Helper fields-------------------------------------------------------------------------

    // endregion Helper fields----------------------------------------------------------------------

    val SUT: UserCreationValidator = UserCreationValidatorImpl

    @Test
    fun `when all strings are not empty should return Valid`() {
        //Arrange
        //Act
        val result = SUT.validate(CORRECT_USER)
        //Assert
        assertEquals(UserCreationValidator.ValidationStatus.Valid(CORRECT_USER), result)
    }

    @Test
    fun `when first and last name are not empty but status message is should return valid`() {
        //Arrange
        val user = CORRECT_USER.copy(statusMessage = EMPTY_STATUS_MESSAGE)
        //Act
        val result = SUT.validate(user)
        //Assert
        assertEquals(
            UserCreationValidator.ValidationStatus.Valid(CORRECT_USER.copy(statusMessage = EMPTY_STATUS_MESSAGE)),
            result
        )
    }

    @Test
    fun `when first name is empty should return Invalid with correct validation points`() {
        //Arrange
        val user = CORRECT_USER.copy(firstName = EMPTY_FIRST_NAME)
        //Act
        val result = SUT.validate(user)
        //Assert
        assertEquals(
            UserCreationValidator.ValidationStatus.Invalid(
                isFirstNameValid = false,
                isLastNameValid = true,
                isStatusMessageValid = true
            ), result
        )
    }

    @Test
    fun `when last name is empty should return Invalid with correct validation points`() {
        //Arrange
        val user = CORRECT_USER.copy(lastName = EMPTY_LAST_NAME)
        //Act
        val result = SUT.validate(user)
        //Assert
        assertEquals(
            UserCreationValidator.ValidationStatus.Invalid(
                isFirstNameValid = true,
                isLastNameValid = false,
                isStatusMessageValid = true
            ), result
        )
    }

    @Test
    fun `when both and last names are empty should return Invalid with correct validation points`() {
        //Arrange
        val user = CORRECT_USER.copy(firstName = EMPTY_FIRST_NAME, lastName = EMPTY_LAST_NAME)
        //Act
        val result = SUT.validate(user)
        //Assert
        assertEquals(
            UserCreationValidator.ValidationStatus.Invalid(
                isFirstNameValid = false,
                isLastNameValid = false,
                isStatusMessageValid = true
            ), result
        )
    }

    // region Helper methods------------------------------------------------------------------------

    // endregion Helper methods----------------------------------------------------------------------

    // region Helper classes------------------------------------------------------------------------

    // endregion Helper classes---------------------------------------------------------------------

}