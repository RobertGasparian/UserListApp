package com.example.userslistapp.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.userslistapp.database.UserDao
import com.example.userslistapp.models.mappers.converters.UserConverter
import com.example.userslistapp.networking.ApiService
import com.example.userslistapp.networking.NetworkService
import com.example.userslistapp.utils.*
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule

class UserRepoTest {

    // region Constants-----------------------------------------------------------------------------

    // endregion Constants--------------------------------------------------------------------------

    // region Helper fields-------------------------------------------------------------------------
    val apiServiceMock = mockk<ApiService>()
    val userDaoMock = mockk<UserDao>()
    val userConverterMock = mockk<UserConverter>()
    val networkServiceMock = mockk<NetworkService>()
    // endregion Helper fields----------------------------------------------------------------------

    @get:Rule
    val testInstantTaskExecutionRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    lateinit var SUT: UserRepo

    @Before
    fun setup() {
        SUT = UserRepoImpl(
            apiServiceMock,
            userDaoMock,
            userConverterMock,
            networkServiceMock,
        )
        converterSetup()
    }

    //get users
    //everything fine, first time fetch
    @Test
    fun `when db is empty data fetched from server`() = mainCoroutineScopeRule.runBlockingTest {
        //Arrange
        emptyDb()
        connectionIsOk()
        successfulFetch()
        successfulInsertingToDb()
        //Act
        SUT.getAllUsers()
        //Assert
        coVerify {
            userDaoMock.getAllUsers()
            apiServiceMock.getUsers()
        }
    }

    @Test
    fun `when db is empty and fetched from server, data inserted in db`() =
        mainCoroutineScopeRule.runBlockingTest {
            //Arrange
            emptyDb()
            connectionIsOk()
            successfulFetch()
            successfulInsertingToDb()
            //Act
            SUT.getAllUsers()
            //Assert
            coVerify {
                userDaoMock.insertAll(*anyVararg())
            }
        }

    @Test
    fun `when fetched from server and added to db, get users from db for returning`() =
        mainCoroutineScopeRule.runBlockingTest {
            //Arrange
            emptyDb()
            connectionIsOk()
            successfulFetch()
            successfulInsertingToDb()
            //Act
            SUT.getAllUsers()
            //Assert
            coVerifyOrder {
                userDaoMock.getAllUsers()
                apiServiceMock.getUsers()
                userDaoMock.insertAll(*anyVararg())
                userDaoMock.getAllUsers()
            }
        }

    //everything fine, already have data
    @Test
    fun `when db is not empty just return Users`() =
        mainCoroutineScopeRule.runBlockingTest {
            //Arrange
            dbIsNotEmpty()
            connectionIsOk()
            //Act
            SUT.getAllUsers()
            //Assert
            coVerify {
                userDaoMock.getAllUsers()
                apiServiceMock wasNot Called
            }
            coVerify(exactly = 0) {
                userDaoMock.insertAll(*anyVararg())
                userDaoMock.delete(any())
            }
        }

    //first time, no network
    @Test
    fun `when db is empty and no connection throw Exception`() =
        mainCoroutineScopeRule.runBlockingTest {
            //Arrange
            emptyDb()
            noConnection()
            var exceptionThrown = false
            //Act
            try {
                SUT.getAllUsers()
            } catch (ex: Exception) {
                exceptionThrown = true
            }
            //Assert
            coVerify {
                userDaoMock.getAllUsers()
                apiServiceMock wasNot Called
            }
            assertTrue(exceptionThrown)
        }

    //first time, server error
    @Test
    fun `when db is empty and fetch failed throw Exception`() =
        mainCoroutineScopeRule.runBlockingTest {
            //Arrange
            emptyDb()
            connectionIsOk()
            fetchFailed()

            var exceptionThrown = false
            //Act
            try {
                SUT.getAllUsers()
            } catch (ex: Exception) {
                exceptionThrown = true
            }
            //Assert
            coVerify {
                userDaoMock.getAllUsers()
                apiServiceMock.getUsers()
            }
            coVerify(exactly = 0) {
                userDaoMock.insertAll(*anyVararg())
                userDaoMock.delete(any())
            }
            assertTrue(exceptionThrown)
        }

    //add user
    //everything fine
    @Test
    fun `when User add to the server it also added to db`() =
        mainCoroutineScopeRule.runBlockingTest {
            //Arrange
            connectionIsOk()
            successfulAdd()
            successfulInsertingToDb()
            //Act
            SUT.addUser(FIRST_NAME, LAST_NAME, STATUS_MESSAGE)
            //Assert
            coVerify {
                apiServiceMock.addUser()
                userDaoMock.insertAll(*anyVararg())
            }
        }

    //no network
    @Test
    fun `when trying to add and no connection, throws Exception`() =
        mainCoroutineScopeRule.runBlockingTest {
            //Arrange
            noConnection()
            var exceptionThrown = false
            //Act
            try {
                SUT.addUser(FIRST_NAME, LAST_NAME, STATUS_MESSAGE)
            } catch (ex: Exception) {
                exceptionThrown = true
            }
            //Assert
            coVerify {
                apiServiceMock wasNot Called
                userDaoMock wasNot Called
            }
            assertTrue(exceptionThrown)
        }

    //server error

    @Test
    fun `when trying to add and server error, throws Exception`() =
        mainCoroutineScopeRule.runBlockingTest {
            //Arrange
            connectionIsOk()
            addFailed()
            var exceptionThrown = false
            //Act
            try {
                SUT.addUser(FIRST_NAME, LAST_NAME, STATUS_MESSAGE)
            } catch (ex: Exception) {
                exceptionThrown = true
            }
            //Assert
            coVerify {
                apiServiceMock.addUser()
                userDaoMock wasNot Called
            }
            assertTrue(exceptionThrown)
        }
    //db error
    @Test
    fun `when added to server but cannot be add to db, throws Exception`() =
        mainCoroutineScopeRule.runBlockingTest {
            //Arrange
            connectionIsOk()
            successfulAdd()
            failedInsertingToDb()
            var exceptionThrown = false
            //Act
            try {
                SUT.addUser(FIRST_NAME, LAST_NAME, STATUS_MESSAGE)
            } catch (ex: Exception) {
                exceptionThrown = true
            }
            //Assert
            coVerify {
                apiServiceMock.addUser()
                userDaoMock.insertAll(*anyVararg())
            }
            assertTrue(exceptionThrown)
        }

    //delete user
    //everything fine
    @Test
    fun `when User deleted from the server it also deleted from db`() =
        mainCoroutineScopeRule.runBlockingTest {
            //Arrange
            connectionIsOk()
            successfulDelete()
            successfulDeletingFromDb()
            //Act
            SUT.delete(CORRECT_USER)
            //Assert
            coVerify {
                apiServiceMock.deleteUser()
                userDaoMock.delete(any())
            }
        }
    //no network
    @Test
    fun `when trying to delete and no connection, throws Exception`() =
        mainCoroutineScopeRule.runBlockingTest {
            //Arrange
            noConnection()
            var exceptionThrown = false
            //Act
            try {
                SUT.delete(CORRECT_USER)
            } catch (ex: Exception) {
                exceptionThrown = true
            }
            //Assert
            coVerify {
                apiServiceMock wasNot Called
                userDaoMock wasNot Called
            }
            assertTrue(exceptionThrown)
        }
    //server error
    @Test
    fun `when trying to delete and server error, throws Exception`() =
        mainCoroutineScopeRule.runBlockingTest {
            //Arrange
            connectionIsOk()
            deleteFailed()
            var exceptionThrown = false
            //Act
            try {
                SUT.delete(CORRECT_USER)
            } catch (ex: Exception) {
                exceptionThrown = true
            }
            //Assert
            coVerify {
                apiServiceMock.deleteUser()
                userDaoMock wasNot Called
            }
            assertTrue(exceptionThrown)
        }
    //db error

    @Test
    fun `when deleted from server but cannot be deleted to db, throws Exception`() =
        mainCoroutineScopeRule.runBlockingTest {
            //Arrange
            connectionIsOk()
            successfulDelete()
            failedDeletingFromDb()
            var exceptionThrown = false
            //Act
            try {
                SUT.delete(CORRECT_USER)
            } catch (ex: Exception) {
                exceptionThrown = true
            }
            //Assert
            coVerify {
                apiServiceMock.deleteUser()
                userDaoMock.delete(any())
            }
            assertTrue(exceptionThrown)
        }

    // region Helper methods------------------------------------------------------------------------
    fun emptyDb() {
        coEvery { userDaoMock.getAllUsers() } returns emptyList()
    }

    fun dbIsNotEmpty() {
        coEvery { userDaoMock.getAllUsers() } returns getUserDBMList()
    }

    fun successfulFetch() {
        coEvery { apiServiceMock.getUsers() } returns getUsersResponse()
    }

    fun fetchFailed() {
        coEvery { apiServiceMock.getUsers() } throws Exception()
    }

    fun successfulAdd() {
        coJustRun { apiServiceMock.addUser() }
    }

    fun addFailed() {
        coEvery { apiServiceMock.addUser() } throws Exception()
    }

    fun successfulDelete() {
        coJustRun { apiServiceMock.deleteUser() }
    }

    fun deleteFailed() {
        coEvery { apiServiceMock.deleteUser() } throws Exception()
    }

    fun connectionIsOk() {
        every { networkServiceMock.isConnected() } returns true
    }

    fun noConnection() {
        every { networkServiceMock.isConnected() } returns false
    }

    fun converterSetup() {
        every { userConverterMock.dtoToDbm(any()) } returns CORRECT_USER_DBM
        every { userConverterMock.dtoToModel(any()) } returns CORRECT_USER
        every { userConverterMock.dbmToDto(any()) } returns PERSON_WITH_ICON
        every { userConverterMock.dbmToModel(any()) } returns CORRECT_USER
        every { userConverterMock.modelToDTO(any()) } returns PERSON_WITH_ICON
        every { userConverterMock.modelToDbm(any()) } returns CORRECT_USER_DBM
    }

    fun successfulInsertingToDb() {
        coJustRun { userDaoMock.insertAll(*anyVararg()) }
    }

    fun failedInsertingToDb() {
        coEvery { userDaoMock.insertAll(*anyVararg()) } throws Exception()
    }

    fun successfulDeletingFromDb() {
        coJustRun { userDaoMock.delete(any()) }
    }

    fun failedDeletingFromDb() {
        coEvery { userDaoMock.delete(any()) } throws Exception()
    }
    // endregion Helper methods----------------------------------------------------------------------

    // region Helper classes------------------------------------------------------------------------

    // endregion Helper classes---------------------------------------------------------------------

}