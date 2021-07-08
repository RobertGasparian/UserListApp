package com.example.userslistapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.userslistapp.misc.UserCreationValidator
import com.example.userslistapp.ui.fragments.dialogs.UIState
import com.example.userslistapp.utils.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule

class AddUserVIewModelTest {

    // region Constants-----------------------------------------------------------------------------
    companion object {
        const val EMPTY_FIRST_NAME = ""
        const val EMPTY_LAST_NAME = ""
        const val EMPTY_STATUS_MESSAGE = ""
    }
    // endregion Constants--------------------------------------------------------------------------

    // region Helper fields-------------------------------------------------------------------------
    val validatorMock = mockk<UserCreationValidator>()
    // endregion Helper fields----------------------------------------------------------------------

    lateinit var SUT: AddUserVIewModel

    val receivedUiStates: MutableList<UIState> = mutableListOf()

    @get:Rule
    val testInstantTaskExecutionRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        SUT = AddUserVIewModelImpl(validatorMock)
    }

    @Test
    fun `when all fields are not empty user successfully added`() {
        //Arrange
        validData()
        observeUiState()
        //Act
        SUT.tryToAdd(FIRST_NAME, LAST_NAME, STATUS_MESSAGE)
        //Assert
        assertEquals(
            listOf(
                UIState.ValidData(CORRECT_USER)
            ), receivedUiStates
        )
    }

    @Test
    fun `when first name is empty returns invalid status with correct error points`() {
        //Arrange
        firstNameInvalid()
        observeUiState()
        //Act
        SUT.tryToAdd(EMPTY_FIRST_NAME, LAST_NAME, STATUS_MESSAGE)
        //Assert
        assertEquals(
            listOf(
                UIState.InvalidData(isFirstNameValid = false, isLastNameValid = true)
            ), receivedUiStates
        )
    }

    @Test
    fun `when last name is empty returns invalid status with correct error points`() {
        //Arrange
        lastNameInvalid()
        observeUiState()
        //Act
        SUT.tryToAdd(FIRST_NAME, EMPTY_LAST_NAME, STATUS_MESSAGE)
        //Assert
        assertEquals(
            listOf(
                UIState.InvalidData(isFirstNameValid = true, isLastNameValid = false)
            ), receivedUiStates
        )
    }

    @Test
    fun `when both names are empty returns invalid status with correct error points`() {
        //Arrange
        bothNamesInvalid()
        observeUiState()
        //Act
        SUT.tryToAdd(EMPTY_FIRST_NAME, EMPTY_LAST_NAME, STATUS_MESSAGE)
        //Assert
        assertEquals(
            listOf(
                UIState.InvalidData(isFirstNameValid = false, isLastNameValid = false)
            ), receivedUiStates
        )
    }

    @Test
    fun `when user starts editing first name, error message must be hidden `() {
        //Arrange
        firstNameInvalid()
        observeUiState()
        //Act
        SUT.tryToAdd(EMPTY_FIRST_NAME, LAST_NAME, STATUS_MESSAGE)
        SUT.firstNameChanged(FIRST_NAME.first().toString())
        //Assert
        assertEquals(
            listOf(
                UIState.InvalidData(isFirstNameValid = false, isLastNameValid = true),
                UIState.HideFirstNameError
            ), receivedUiStates
        )
    }


    @Test
    fun `when user starts editing last name, error message must be hidden `() {
        //Arrange
        lastNameInvalid()
        observeUiState()
        //Act
        SUT.tryToAdd(FIRST_NAME, EMPTY_LAST_NAME, STATUS_MESSAGE)
        SUT.lastNameChanged(LAST_NAME.first().toString())
        //Assert
        assertEquals(
            listOf(
                UIState.InvalidData(isFirstNameValid = true, isLastNameValid = false),
                UIState.HideLastNameError
            ), receivedUiStates
        )
    }

    // region Helper methods------------------------------------------------------------------------
    fun observeUiState() {
        SUT.uiState().observeForever {
            if (it != null) receivedUiStates.add(it)
        }
    }

    fun validData() {
        every { validatorMock.validate(any()) } returns UserCreationValidator.ValidationStatus.Valid(
            CORRECT_USER
        )
    }

    fun firstNameInvalid() {
        every { validatorMock.validate(any()) } returns UserCreationValidator.ValidationStatus.Invalid(
            isFirstNameValid = false,
            isLastNameValid = true,
            isStatusMessageValid = true,
        )
    }

    fun lastNameInvalid() {
        every { validatorMock.validate(any()) } returns UserCreationValidator.ValidationStatus.Invalid(
            isFirstNameValid = true,
            isLastNameValid = false,
            isStatusMessageValid = true,
        )
    }

    fun bothNamesInvalid() {
        every { validatorMock.validate(any()) } returns UserCreationValidator.ValidationStatus.Invalid(
            isFirstNameValid = false,
            isLastNameValid = false,
            isStatusMessageValid = true,
        )
    }
    // endregion Helper methods----------------------------------------------------------------------

    // region Helper classes------------------------------------------------------------------------

    // endregion Helper classes---------------------------------------------------------------------

}