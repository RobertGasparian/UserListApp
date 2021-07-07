package com.example.userslistapp.models.mappers

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dto.PersonDTO
import com.example.userslistapp.utils.CORRECT_USER
import com.example.userslistapp.utils.PERSON_NO_ICON
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

class UserToPersonMapperTest {

    // region Constants-----------------------------------------------------------------------------

    // endregion Constants--------------------------------------------------------------------------

    // region Helper fields-------------------------------------------------------------------------

    // endregion Helper fields----------------------------------------------------------------------

    val SUT: Mapper<User, PersonDTO> = UserToPersonMapper

    @Test
    fun `correct PersonDTO object returned from User mapping`() {
        //Arrange
        val user = CORRECT_USER
        //Act
        val result = SUT.map(user)
        //Assert
        assertEquals(PERSON_NO_ICON, result)
    }

    // region Helper methods------------------------------------------------------------------------

    // endregion Helper methods----------------------------------------------------------------------

    // region Helper classes------------------------------------------------------------------------

    // endregion Helper classes---------------------------------------------------------------------

}