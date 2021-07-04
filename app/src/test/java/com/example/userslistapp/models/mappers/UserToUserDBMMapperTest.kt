package com.example.userslistapp.models.mappers

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dbm.UserDBM
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


import org.junit.Assert.*

class UserToUserDBMMapperTest {

    // region Constants-----------------------------------------------------------------------------
    companion object {
        const val FIRST_NAME = "first name"
        const val LAST_NAME = "last name"
        const val STATUS_MESSAGE = "status message"
        val CORRECT_USER_DBM = UserDBM(
            firstName = FIRST_NAME,
            lastName = LAST_NAME,
            statusMessage = STATUS_MESSAGE,
        )
    }
    // endregion Constants--------------------------------------------------------------------------

    // region Helper fields-------------------------------------------------------------------------

    // endregion Helper fields----------------------------------------------------------------------

    val SUT: Mapper<User, UserDBM> = UserToUserDBMMapper

    @Test
    fun `correct UserDBM object returned from User mapping`() {
        //Arrange
        val user = User(
            firstName = FIRST_NAME,
            lastName = LAST_NAME,
            statusMessage = STATUS_MESSAGE
        )
        //Act
        val result = SUT.map(user)
        //Assert
        assertEquals(CORRECT_USER_DBM, result)
    }

    // region Helper methods------------------------------------------------------------------------

    // endregion Helper methods----------------------------------------------------------------------

    // region Helper classes------------------------------------------------------------------------

    // endregion Helper classes---------------------------------------------------------------------

}