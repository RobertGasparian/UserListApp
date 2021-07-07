package com.example.userslistapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.userslistapp.ui.fragments.UIState
import com.example.userslistapp.usecases.AddUserUseCase
import com.example.userslistapp.usecases.DeleteUserUseCase
import com.example.userslistapp.usecases.GetAllUsersUseCase
import com.example.userslistapp.utils.*
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*

import org.junit.Assert.*
import org.junit.rules.TestRule
import java.lang.Exception

class UserListViewModelTest {

    // region Constants-----------------------------------------------------------------------------
    companion object {
        const val ERROR_MESSAGE = "Error Message"
    }
    // endregion Constants--------------------------------------------------------------------------

    // region Helper fields-------------------------------------------------------------------------
    val getAllUsersUseCaseMock: GetAllUsersUseCase = mockk()
    val addUserUseCaseMock: AddUserUseCase = mockk()
    val deleteUserUseCaseMock: DeleteUserUseCase = mockk()
    // endregion Helper fields----------------------------------------------------------------------

    lateinit var SUT: UserListViewModel

    val receivedUiStates: MutableList<UIState> = mutableListOf()

    @get:Rule
    val testInstantTaskExecutionRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        SUT = UserListViewModelImpl(getAllUsersUseCaseMock, addUserUseCaseMock, deleteUserUseCaseMock, mainCoroutineScopeRule.testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        receivedUiStates.clear()
    }

    @Test
    fun `should return Success when successfully gets all Users`() {
        //Arrange
        getUsersSuccess()
        observeUiState()
        //Act
        SUT.getUsers()
        //Assert
        assertEquals(listOf(
            UIState.Loading,
            UIState.Success(getUserList())
        ), receivedUiStates)
        coVerify {
            getAllUsersUseCaseMock.getAllUsers()
            addUserUseCaseMock wasNot Called
            deleteUserUseCaseMock wasNot Called
        }
    }

    @Test
    fun `should return Error when something went wrong`() {
        //Arrange
        getUsersFailure()
        observeUiState()
        //Act
        SUT.getUsers()
        //Assert
        assertEquals(listOf(
            UIState.Loading,
            UIState.Error(ERROR_MESSAGE)
        ), receivedUiStates)
        coVerify {
            getAllUsersUseCaseMock.getAllUsers()
            addUserUseCaseMock wasNot Called
            deleteUserUseCaseMock wasNot Called
        }
    }

    @Test
    fun `should open Delete dialog when the user tries to delete a User`() {
        //Arrange
        observeUiState()
        //Act
        SUT.tryToDelete(CORRECT_USER)
        //Assert
        assertEquals(
            listOf(
                UIState.DeleteDialog(CORRECT_USER)
            ), receivedUiStates
        )
    }

    @Test
    fun `should open Add dialog when the user tries to add a new User`() {
        //Arrange
        observeUiState()
        //Act
        SUT.tryToAdd()
        //Assert
        assertEquals(
            listOf(
                UIState.AddUserDialog
            ), receivedUiStates
        )

    }

    @Test
    fun `when the user canceled deletion nothing happens`() {
        //Arrange
        observeUiState()
        //Act
        SUT.cancelDeleteAction()
        //Assert
        assertEquals(emptyList<UIState>(), receivedUiStates)
    }

    @Test
    fun `when the user canceled adding nothing happens`() {
        //Arrange
        observeUiState()
        //Act
        SUT.cancelAddAction()
        //Assert
        assertEquals(emptyList<UIState>(), receivedUiStates)
    }

    @Test
    fun `when the user confirms adding and its succeeded Users list must be refreshed`() {
        //Arrange
        addUserSuccessfully()
        getUsersSuccess()
        observeUiState()
        //Act
        SUT.addUser(FIRST_NAME, LAST_NAME, STATUS_MESSAGE)
        //Assert
        assertEquals(
            listOf(
                UIState.Loading,
                UIState.Success(getUserList())
            ), receivedUiStates
        )
        coVerify {
            addUserUseCaseMock.addUser(any(), any(), any())
            getAllUsersUseCaseMock.getAllUsers()
            deleteUserUseCaseMock wasNot Called
        }
    }

    @Test
    fun `after confirming Add action and User Add fails should return Error`() {
        //Arrange
        addingUserFailed()
        getUsersSuccess()
        observeUiState()
        //Act
        SUT.addUser(FIRST_NAME, LAST_NAME, STATUS_MESSAGE)
        //Assert
        assertEquals(
            listOf(
                UIState.Loading,
                UIState.Error(ERROR_MESSAGE)
            ), receivedUiStates
        )
        coVerify {
            addUserUseCaseMock.addUser(any(), any(), any())
            getAllUsersUseCaseMock wasNot Called
            deleteUserUseCaseMock wasNot Called
        }
    }

    @Test
    fun `after confirming Add action and getting all Users fails should return Error`() {
        //Arrange
        addUserSuccessfully()
        getUsersFailure()
        observeUiState()
        //Act
        SUT.addUser(FIRST_NAME, LAST_NAME, STATUS_MESSAGE)
        //Assert
        assertEquals(
            listOf(
                UIState.Loading,
                UIState.Error(ERROR_MESSAGE)
            ), receivedUiStates
        )
        coVerify {
            addUserUseCaseMock.addUser(any(), any(), any())
            getAllUsersUseCaseMock.getAllUsers()
            deleteUserUseCaseMock wasNot Called
        }
    }

    @Test
    fun `when the user confirms deleting and its succeeded Users list must be refreshed`() {
        //Arrange
        deleteUserSuccessfully()
        getUsersSuccess()
        observeUiState()
        //Act
        SUT.deleteUser(CORRECT_USER)
        //Assert
        assertEquals(
            listOf(
                UIState.Loading,
                UIState.Success(getUserList())
            ), receivedUiStates
        )
        coVerify {
            deleteUserUseCaseMock.deleteUser(any())
            getAllUsersUseCaseMock.getAllUsers()
            addUserUseCaseMock wasNot Called
        }
    }

    @Test
    fun `after confirming Delete action and User Delete fails should return Error`() {
        //Arrange
        deletingUserFailed()
        getUsersSuccess()
        observeUiState()
        //Act
        SUT.deleteUser(CORRECT_USER)
        //Assert
        assertEquals(
            listOf(
                UIState.Loading,
                UIState.Error(ERROR_MESSAGE)
            ), receivedUiStates
        )
        coVerify {
            deleteUserUseCaseMock.deleteUser(any())
            getAllUsersUseCaseMock wasNot Called
            addUserUseCaseMock wasNot Called
        }
    }

    @Test
    fun `after confirming Delete action and getting all Users fails should return Error`() {
        //Arrange
        deleteUserSuccessfully()
        getUsersFailure()
        observeUiState()
        //Act
        SUT.deleteUser(CORRECT_USER)
        //Assert
        assertEquals(
            listOf(
                UIState.Loading,
                UIState.Error(ERROR_MESSAGE)
            ), receivedUiStates
        )
        coVerify {
            deleteUserUseCaseMock.deleteUser(any())
            getAllUsersUseCaseMock.getAllUsers()
            addUserUseCaseMock wasNot Called
        }
    }

    // region Helper methods------------------------------------------------------------------------
    fun getUsersSuccess() {
        coEvery { getAllUsersUseCaseMock.getAllUsers() } returns getUserList()
    }

    fun getUsersFailure() {
        coEvery { getAllUsersUseCaseMock.getAllUsers() } throws Exception(ERROR_MESSAGE)
    }

    fun addUserSuccessfully() {
        coEvery { addUserUseCaseMock.addUser(any(), any(), any()) } just Runs
    }

    fun addingUserFailed() {
        coEvery { addUserUseCaseMock.addUser(any(), any(), any()) } throws Exception(ERROR_MESSAGE)
    }

    fun deleteUserSuccessfully() {
        coEvery { deleteUserUseCaseMock.deleteUser(any()) } just Runs
    }

    fun deletingUserFailed() {
        coEvery { deleteUserUseCaseMock.deleteUser(any()) } throws Exception(ERROR_MESSAGE)
    }

    fun observeUiState() {
        SUT.uiState().observeForever {
            if (it != null) receivedUiStates.add(it)
        }
    }
    // endregion Helper methods----------------------------------------------------------------------

    // region Helper classes------------------------------------------------------------------------

    // endregion Helper classes---------------------------------------------------------------------

}