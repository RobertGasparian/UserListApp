package com.example.userslistapp.utils

import com.example.userslistapp.models.appmodels.User
import com.example.userslistapp.models.dbm.UserDBM
import com.example.userslistapp.models.dto.GroupDTO
import com.example.userslistapp.models.dto.PersonDTO
import com.example.userslistapp.models.responses.UsersResponse

const val FIRST_NAME = "first name"
const val LAST_NAME = "last name"
const val STATUS_MESSAGE = "status message"
const val STATUS_ICON = "status icon"
const val GROUP_NAME = "group name"

const val ERROR_MESSAGE = "Error Message"

val CORRECT_USER = User(
    FIRST_NAME,
    LAST_NAME,
    STATUS_MESSAGE,
)

val PERSON_NO_ICON = PersonDTO(
    FIRST_NAME,
    LAST_NAME,
    null,
    STATUS_MESSAGE,
)

val PERSON_WITH_ICON = PersonDTO(
    FIRST_NAME,
    LAST_NAME,
    STATUS_ICON,
    STATUS_MESSAGE,
)

val CORRECT_USER_DBM = UserDBM(
    firstName = FIRST_NAME,
    lastName = LAST_NAME,
    statusMessage = STATUS_MESSAGE,
)

fun getUserList(count: Int = 3): List<User> {
    val list = mutableListOf<User>()
    repeat(count) {
        list.add(
            User(
                "$FIRST_NAME$it",
                "$LAST_NAME$it",
                "$STATUS_MESSAGE$it",
            )
        )
    }
    return list
}

fun getUsersResponse(groupCount: Int = 3)= UsersResponse(getGroupsDtoList(groupCount))

fun getGroupsDtoList(groupCount: Int = 3): List<GroupDTO> {
    val list = mutableListOf<GroupDTO>()
    repeat(groupCount) {
        list.add(
            GroupDTO(
                "$GROUP_NAME $it",
                getPersonsList()
            )
        )
    }
    return list
}

fun getPersonsList(count: Int = 3): List<PersonDTO> {
    val list = mutableListOf<PersonDTO>()
    repeat(count) {
        list.add(
            PersonDTO(
                "$FIRST_NAME $it",
                "$LAST_NAME $it",
                "$STATUS_ICON $it",
                "$STATUS_MESSAGE $it"
            )
        )
    }
    return list
}

fun getUserDBMList(count: Int = 3): List<UserDBM> {
    val list = mutableListOf<UserDBM>()
    repeat(count) {
        list.add(
            UserDBM(
                "$FIRST_NAME $it",
                "$LAST_NAME $it",
                "$STATUS_ICON $it",
                "$STATUS_MESSAGE $it"
            )
        )
    }
    return list
}