package com.example.userslistapp.models.mappers

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dbm.UserDBM
import com.example.userslistapp.models.dto.PersonDTO
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

class PersonToUserDBMMapperTest {

    // region Constants-----------------------------------------------------------------------------
    companion object {
        const val FIRST_NAME = "first name"
        const val LAST_NAME = "last name"
        const val STATUS_MESSAGE = "status message"
        const val STATUS_ICON = "status icon"
        val MINIMAL_CORRECT_USER_DBM = UserDBM(
            uid = FIRST_NAME + LAST_NAME,
            firstName = FIRST_NAME,
            lastName = LAST_NAME,
            statusMessage = STATUS_MESSAGE,
            statusIcon = null)
        val COMPLETE_USER_DBM = UserDBM(
            uid = FIRST_NAME + LAST_NAME,
            firstName = FIRST_NAME,
            lastName = LAST_NAME,
            statusMessage = STATUS_MESSAGE,
            statusIcon = STATUS_ICON)
    }
    // endregion Constants--------------------------------------------------------------------------

    // region Helper fields-------------------------------------------------------------------------

    // endregion Helper fields----------------------------------------------------------------------

    val SUT: Mapper<PersonDTO, UserDBM?> = PersonToUserDBMMapper

    //success case
    @Test
    fun `when PersonDTO has all necessary data returns correct UserDBM object`() {
        //Arrange
        val personDto = PersonDTO(
            firstName = FIRST_NAME,
            lastName = LAST_NAME,
            statusMessage = STATUS_MESSAGE,
            statusIcon = null,
        )
        //Act
        val result = SUT.map(personDto)
        //Assert
        assertNotNull(result)
        assertEquals(MINIMAL_CORRECT_USER_DBM, result)
    }

    @Test
    fun `when PersonDTO has all data returns correct UserDBM object with all additional fields`() {
        //Arrange
        val personDto = PersonDTO(
            firstName = FIRST_NAME,
            lastName = LAST_NAME,
            statusMessage = STATUS_MESSAGE,
            statusIcon = STATUS_ICON,
        )
        //Act
        val result = SUT.map(personDto)
        //Assert
        assertNotNull(result)
        assertEquals(COMPLETE_USER_DBM, result)
    }

    //failure case
    @Test
    fun `when PersonDTO has incomplete data returns null`() {
        //Arrange
        val person1 = PersonDTO(
            firstName = PersonToUserMapperTest.FIRST_NAME,
            lastName = PersonToUserMapperTest.LAST_NAME,
        )
        val person2 = PersonDTO(
            firstName = PersonToUserMapperTest.FIRST_NAME,
            statusMessage = PersonToUserMapperTest.STATUS_MESSAGE,
        )
        val person3 = PersonDTO(
            statusMessage = PersonToUserMapperTest.STATUS_MESSAGE,
            lastName = PersonToUserMapperTest.LAST_NAME,
        )
        val person4 = PersonDTO()
        //Act
        val result1 = SUT.map(person1)
        val result2 = SUT.map(person2)
        val result3 = SUT.map(person3)
        val result4 = SUT.map(person4)
        //Assert
        assertNull(result1)
        assertNull(result2)
        assertNull(result3)
        assertNull(result4)
    }

    // region Helper methods------------------------------------------------------------------------

    // endregion Helper methods----------------------------------------------------------------------

    // region Helper classes------------------------------------------------------------------------

    // endregion Helper classes---------------------------------------------------------------------

}