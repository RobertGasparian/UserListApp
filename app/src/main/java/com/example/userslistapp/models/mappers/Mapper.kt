package com.example.userslistapp.models.mappers

interface Mapper<I, O> {
    fun map(input: I): O
}

//TODO: impl later