package com.example.userslistapp.models.mappers

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dbm.UserDBM
import com.example.userslistapp.utils.CORRECT_USER
import com.example.userslistapp.utils.CORRECT_USER_DBM
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


import org.junit.Assert.*

class UserToUserDBMMapperTest {

    // region Constants-----------------------------------------------------------------------------

    // endregion Constants--------------------------------------------------------------------------

    // region Helper fields-------------------------------------------------------------------------

    // endregion Helper fields----------------------------------------------------------------------

    val SUT: Mapper<User, UserDBM> = UserToUserDBMMapper

    @Test
    fun `correct UserDBM object returned from User mapping`() {
        //Arrange
        val user = CORRECT_USER
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