package com.example.userslistapp.models.mappers

import com.example.userslistapp.misc.safeLet
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dto.PersonDTO

object PersonToUserMapper: Mapper<PersonDTO, User?> {
    override fun map(input: PersonDTO): User? {
        return safeLet(input.firstName, input.lastName, input.statusMessage) { firstName, lastName, statusMessage ->
            User(firstName, lastName, statusMessage)
        }
    }
}
