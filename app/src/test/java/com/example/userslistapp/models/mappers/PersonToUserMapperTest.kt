package com.example.userslistapp.models.mappers

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dto.PersonDTO
import com.example.userslistapp.utils.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*


class PersonToUserMapperTest {

    // region Constants-----------------------------------------------------------------------------

    // endregion Constants--------------------------------------------------------------------------

    // region Helper fields-------------------------------------------------------------------------

    // endregion Helper fields----------------------------------------------------------------------

    val SUT: Mapper<PersonDTO, User?> = PersonToUserMapper

    //success case
    @Test
    fun `when PersonDTO has all necessary data returns correct User object`()  {
        //Arrange
        val personDTO = PERSON_NO_ICON
        //Act
        val result = SUT.map(personDTO)
        //Assert
        assertNotNull(result)
        assertEquals(CORRECT_USER, result)
    }

    //failure case
    @Test
    fun `when PersonDTO has incomplete data returns null`() {
        //Arrange
        val person1 = PersonDTO(
            firstName = FIRST_NAME,
            lastName = LAST_NAME,
        )
        val person2 = PersonDTO(
            firstName = FIRST_NAME,
            statusMessage = STATUS_MESSAGE,
        )
        val person3 = PersonDTO(
            statusMessage = STATUS_MESSAGE,
            lastName = LAST_NAME,
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