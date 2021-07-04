package com.example.userslistapp.models.mappers.converters

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dbm.UserDBM
import com.example.userslistapp.models.dto.PersonDTO
import com.example.userslistapp.models.mappers.Mapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class UserConverterImplTest {

    // region Constants-----------------------------------------------------------------------------
    companion object {
        const val FIRST_NAME = "first name"
        const val LAST_NAME = "last name"
        const val STATUS_MESSAGE = "status message"
        const val STATUS_ICON = "status icon"
    }
    // endregion Constants--------------------------------------------------------------------------

    // region Helper fields-------------------------------------------------------------------------
    val modelToDtoMapperMock = mockk<Mapper<User, PersonDTO>>()
    val modelToDbmMapperMock = mockk<Mapper<User, UserDBM>>()
    val dbmToDtoMapperMock = mockk<Mapper<UserDBM, PersonDTO>>()
    val dbmToModelMapperMock = mockk<Mapper<UserDBM, User>>()
    val dtoToDbmMapperMock = mockk<Mapper<PersonDTO, UserDBM?>>()
    val dtoToModelMapperMock = mockk<Mapper<PersonDTO, User?>>()
    // endregion Helper fields----------------------------------------------------------------------

    lateinit var SUT: UserConverter

    @Before
    fun setup() {
        SUT = UserConverterImpl(
            dtoToModelMapperMock,
            dtoToDbmMapperMock,
            modelToDbmMapperMock,
            modelToDtoMapperMock,
            dbmToModelMapperMock,
            dbmToDtoMapperMock
        )
    }

    @Test
    fun `modelToDTO returns correct DTO`() {
        // Arrange
        val model = getModel()
        setupModelToDtoMock()
        // Act
        val result = SUT.modelToDTO(model)
        // Assert
        assertEquals(getDto(statusIcon = null), result)
        verify {
            modelToDtoMapperMock.map(model)
        }
    }

    @Test
    fun `dtoToModel with complete data returns correct Model`() {
        // Arrange
        val dto = getDto()
        setupSuccessDtoToModelMock()
        // Act
        val result = SUT.dtoToModel(dto)
        // Assert
        assertNotNull(result)
        assertEquals(getModel(), result)
        verify {
            dtoToModelMapperMock.map(dto)
        }
    }

    @Test
    fun `dtoToModel with incomplete data returns null`() {
        // Arrange
        val dto1 = getDto(firstName = null)
        val dto2 = getDto(lastName = null)
        val dto3 = getDto(statusMessage = null)
        val dto4 = getDto(firstName = null, lastName = null, statusMessage = null)
        setupFailureDtoToModelMock()
        // Act
        val result1 = SUT.dtoToModel(dto1)
        val result2 = SUT.dtoToModel(dto2)
        val result3 = SUT.dtoToModel(dto3)
        val result4 = SUT.dtoToModel(dto4)
        // Assert
        assertNull(result1)
        assertNull(result2)
        assertNull(result3)
        assertNull(result4)
        verify {
            dtoToModelMapperMock.map(dto1)
            dtoToModelMapperMock.map(dto2)
            dtoToModelMapperMock.map(dto3)
            dtoToModelMapperMock.map(dto4)
        }
    }

    @Test
    fun `modelToDbm returns correct DBM`() {
        // Arrange
        val model = getModel()
        setupModelToDbmMock()
        // Act
        val result = SUT.modelToDbm(model)
        // Assert
        assertEquals(getDbm(statusIcon = null), result)
        verify {
            modelToDbmMapperMock.map(model)
        }
    }

    @Test
    fun `dbmToModel DBM with statusIcon returns correct Model`() {
        // Arrange
        val dbm = getDbm()
        setupDbmToModelMock(true)
        // Act
        val result = SUT.dbmToModel(dbm)
        // Assert
        assertEquals(getModel(), result)
        verify {
            dbmToModelMapperMock.map(dbm)
        }
    }

    @Test
    fun `dbmToModel DBM without statusIcon returns correct Model`() {
        // Arrange
        val dbm = getDbm(statusIcon = null)
        setupDbmToModelMock(false)
        // Act
        val result = SUT.dbmToModel(dbm)
        // Assert
        assertEquals(getModel(), result)
        verify {
            dbmToModelMapperMock.map(dbm)
        }
    }

    @Test
    fun `dtoToDbm with complete data returns correct DBM`() {
        // Arrange
        val dto = getDto()
        setupSuccessDtoToDbmMock()
        // Act
        val result = SUT.dtoToDbm(dto)
        // Assert
        assertNotNull(result)
        assertEquals(getDbm(), result)
        verify {
            dtoToDbmMapperMock.map(dto)
        }
    }

    @Test
    fun `dtoToDbm with incomplete data returns null`() {
        // Arrange
        val dto1 = getDto(firstName = null)
        val dto2 = getDto(lastName = null)
        val dto3 = getDto(statusMessage = null)
        val dto4 = getDto(firstName = null, lastName = null, statusMessage = null)
        setupFailureDtoToDbmMock()
        // Act
        val result1 = SUT.dtoToDbm(dto1)
        val result2 = SUT.dtoToDbm(dto2)
        val result3 = SUT.dtoToDbm(dto3)
        val result4 = SUT.dtoToDbm(dto4)
        // Assert
        assertNull(result1)
        assertNull(result2)
        assertNull(result3)
        assertNull(result4)
        verify {
            dtoToDbmMapperMock.map(dto1)
            dtoToDbmMapperMock.map(dto2)
            dtoToDbmMapperMock.map(dto3)
            dtoToDbmMapperMock.map(dto4)
        }
    }

    @Test
    fun `dbmToDto DBM with statusIcon returns correct DTO`() {
        // Arrange
        val dbm = getDbm()
        setupDbmToDtoMock(true)
        // Act
        val result = SUT.dbmToDto(dbm)
        // Assert
        assertEquals(getDto(), result)
        verify {
            dbmToDtoMapperMock.map(dbm)
        }
    }

    @Test
    fun `dbmToDto DBM without statusIcon returns correct DTO`() {
        // Arrange
        val dbm = getDbm(statusIcon = null)
        setupDbmToDtoMock(false)
        // Act
        val result = SUT.dbmToDto(dbm)
        // Assert
        assertEquals(getDto(statusIcon = null), result)
        verify {
            dbmToDtoMapperMock.map(dbm)
        }
    }

    // region Helper methods------------------------------------------------------------------------
    fun getModel(): User {
        return User(
            FIRST_NAME,
            LAST_NAME,
            STATUS_MESSAGE
        )
    }

    fun getDto(
        firstName: String? = FIRST_NAME,
        lastName: String? = LAST_NAME,
        statusMessage: String? = STATUS_MESSAGE,
        statusIcon: String? = STATUS_ICON
    ): PersonDTO {
        return PersonDTO(
            firstName,
            lastName,
            statusIcon,
            statusMessage
        )
    }

    fun getDbm(statusIcon: String? = STATUS_ICON): UserDBM {
        return UserDBM(
            FIRST_NAME,
            LAST_NAME,
            STATUS_MESSAGE,
        )
    }

    fun setupModelToDtoMock() {
        every { modelToDtoMapperMock.map(any()) } returns getDto(statusIcon = null)
    }

    fun setupModelToDbmMock() {
        every { modelToDbmMapperMock.map(any()) } returns getDbm(statusIcon = null)
    }

    fun setupDbmToModelMock(withStatusIcon: Boolean) {
        if (withStatusIcon) {
            every { dbmToModelMapperMock.map(any()) } returns getModel()
        } else {
            every { dbmToModelMapperMock.map(any()) } returns getModel()
        }
    }

    fun setupDbmToDtoMock(withStatusIcon: Boolean) {
        if (withStatusIcon) {
            every { dbmToDtoMapperMock.map(any()) } returns getDto()
        } else {
            every { dbmToDtoMapperMock.map(any()) } returns getDto(statusIcon = null)
        }
    }

    fun setupSuccessDtoToModelMock() {
        every { dtoToModelMapperMock.map(any()) } returns getModel()
    }

    fun setupFailureDtoToModelMock() {
        every { dtoToModelMapperMock.map(any()) } returns null
    }

    fun setupSuccessDtoToDbmMock() {
        every { dtoToDbmMapperMock.map(any()) } returns getDbm()
    }

    fun setupFailureDtoToDbmMock() {
        every { dtoToDbmMapperMock.map(any()) } returns null
    }
    // endregion Helper methods----------------------------------------------------------------------

    // region Helper classes------------------------------------------------------------------------

    // endregion Helper classes---------------------------------------------------------------------

}