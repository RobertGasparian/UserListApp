package com.example.userslistapp.models.mappers

import com.example.userslistapp.misc.safeLet
import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dbm.UserDBM
import com.example.userslistapp.models.dto.PersonDTO

object PersonToUserMapper: Mapper<PersonDTO, User?> {
    override fun map(input: PersonDTO): User? {
        return safeLet(input.firstName, input.lastName, input.statusMessage) { firstName, lastName, statusMessage ->
            User(firstName, lastName, statusMessage)
        }
    }
}

object PersonToUserDBMMapper: Mapper<PersonDTO, UserDBM?> {
    override fun map(input: PersonDTO): UserDBM? {
        return safeLet(input.firstName, input.lastName, input.statusMessage) { firstName, lastName, statusMessage ->
            UserDBM(
                uid = firstName + lastName,
                firstName = firstName,
                lastName = lastName,
                statusMessage = statusMessage,
                statusIcon = input.statusIcon
            )
        }
    }
}

object DBMToUserMapper: Mapper<UserDBM, User> {
    override fun map(input: UserDBM): User {
        return User(
            input.firstName,
            input.lastName,
            input.statusMessage
        )
    }

}
