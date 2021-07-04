package com.example.userslistapp.models.mappers

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dto.PersonDTO
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

class UserToPersonMapperTest {

    // region Constants-----------------------------------------------------------------------------
    companion object {
        const val FIRST_NAME = "first name"
        const val LAST_NAME = "last name"
        const val STATUS_MESSAGE = "status message"
        val CORRECT_PERSON = PersonDTO(
            FIRST_NAME,
            LAST_NAME,
            null,
            STATUS_MESSAGE,
        )
    }
    // endregion Constants--------------------------------------------------------------------------

    // region Helper fields-------------------------------------------------------------------------

    // endregion Helper fields----------------------------------------------------------------------

    val SUT: Mapper<User, PersonDTO> = UserToPersonMapper

    @Test
    fun `correct PersonDTO object returned from User mapping`() {
        //Arrange
        val user = User(
            FIRST_NAME,
            LAST_NAME,
            STATUS_MESSAGE
        )
        //Act
        val result = SUT.map(user)
        //Assert
        assertEquals(CORRECT_PERSON, result)
    }

    // region Helper methods------------------------------------------------------------------------

    // endregion Helper methods----------------------------------------------------------------------

    // region Helper classes------------------------------------------------------------------------

    // endregion Helper classes---------------------------------------------------------------------

}