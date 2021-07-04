package com.example.userslistapp.models.mappers.converters

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dbm.UserDBM
import com.example.userslistapp.models.dto.PersonDTO
import com.example.userslistapp.models.mappers.Mapper

/**
 * API that gives us easy and short way to switch between model/DTO/DBM structures
 * NOTE: In some cases there may be data loss, because not all structures contains all
 * data from others (that is the point of this approach)
 */
interface UserConverter: Converter<User, PersonDTO, UserDBM>

class UserConverterImpl(
    private val personToUser: Mapper<PersonDTO, User?>,
    private val personToUserDbm: Mapper<PersonDTO, UserDBM?>,
    private val userToUserDBM: Mapper<User, UserDBM>,
    private val userToPerson: Mapper<User, PersonDTO>,
    private val dbmToUser: Mapper<UserDBM, User>,
    private val dbmToPerson: Mapper<UserDBM, PersonDTO>
): UserConverter {
    override fun modelToDTO(model: User) = userToPerson.map(model)

    override fun dtoToModel(dto: PersonDTO) = personToUser.map(dto)

    override fun modelToDbm(model: User) = userToUserDBM.map(model)

    override fun dbmToModel(dbm: UserDBM) = dbmToUser.map(dbm)

    override fun dtoToDbm(dto: PersonDTO) = personToUserDbm.map(dto)

    override fun dbmToDto(dbm: UserDBM) = dbmToPerson.map(dbm)
}