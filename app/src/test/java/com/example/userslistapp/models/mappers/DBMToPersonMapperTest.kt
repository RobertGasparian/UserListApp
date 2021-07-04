package com.example.userslistapp.models.mappers

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dbm.UserDBM
import com.example.userslistapp.models.dto.PersonDTO
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

class DBMToPersonMapperTest {

    // region Constants-----------------------------------------------------------------------------
    companion object {
        const val FIRST_NAME = "first name"
        const val LAST_NAME = "last name"
        const val STATUS_MESSAGE = "status message"
        const val STATUS_ICON = "status icon"
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

    val SUT: Mapper<UserDBM, PersonDTO> = DBMToPersonMapper

    @Test
    fun `correct PersonDTO object returned after UserDBM mapping`() {
        //Arrange
        val userDbm = UserDBM(
            firstName = FIRST_NAME,
            lastName = LAST_NAME,
            statusMessage = STATUS_MESSAGE,
        )
        //Act
        val result = SUT.map(userDbm)
        assertEquals(CORRECT_PERSON, result)
    }

    // region Helper methods------------------------------------------------------------------------

    // endregion Helper methods----------------------------------------------------------------------

    // region Helper classes------------------------------------------------------------------------

    // endregion Helper classes---------------------------------------------------------------------

}