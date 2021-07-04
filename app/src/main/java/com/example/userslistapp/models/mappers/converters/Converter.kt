package com.example.userslistapp.models.mappers.converters

interface Converter<Model, DTO, DBM> {
    fun modelToDTO(model: Model): DTO
    fun dtoToModel(dto: DTO): Model?
    fun modelToDbm(model: Model): DBM
    fun dbmToModel(dbm: DBM): Model
    fun dtoToDbm(dto: DTO): DBM?
    fun dbmToDto(dbm: DBM): DTO
}