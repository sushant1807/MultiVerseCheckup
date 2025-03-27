package com.saika.multiversecheckup.data.remote.dto

import com.saika.multiversecheckup.domain.model.Character

data class CharacterDto(
    val id: Int,
    val name: String,
    val image: String,
    val species: String,
    val status: String,
    val origin: OriginDto,
    val type: String?,
    val created: String
)

data class OriginDto(val name: String)

data class CharacterResponseDto(
    val info: InfoDto,
    val results: List<CharacterDto>
)

data class InfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

fun CharacterDto.toDomain() = Character(
    id, name, image, species, status, origin.name, type, created
)