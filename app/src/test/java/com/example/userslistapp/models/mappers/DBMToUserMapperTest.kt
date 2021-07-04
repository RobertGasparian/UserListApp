package com.example.userslistapp.models.mappers

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dbm.UserDBM
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

class DBMToUserMapperTest {

    // region Constants-----------------------------------------------------------------------------
    companion object {
        const val FIRST_NAME = "first name"
        const val LAST_NAME = "last name"
        const val STATUS_MESSAGE = "status message"
        val CORRECT_USER = User(
            FIRST_NAME,
            LAST_NAME,
            STATUS_MESSAGE
        )
    }
    // endregion Constants--------------------------------------------------------------------------

    // region Helper fields-------------------------------------------------------------------------

    // endregion Helper fields----------------------------------------------------------------------

    val SUT: DBMToUserMapper = DBMToUserMapper

    @Test
    fun `correct User object returned from UserDMB mapping`() {
        //Arrange
        val userDbm = UserDBM(
            uid = FIRST_NAME + LAST_NAME,
            firstName = FIRST_NAME,
            lastName = LAST_NAME,
            statusMessage = STATUS_MESSAGE,
            statusIcon = null
        )
        //Act
        val result = SUT.map(userDbm)
        //Assert
        assertEquals(CORRECT_USER, result)
    }

    // region Helper methods------------------------------------------------------------------------

    // endregion Helper methods----------------------------------------------------------------------

    // region Helper classes------------------------------------------------------------------------

    // endregion Helper classes---------------------------------------------------------------------

}