package com.saika.multiversecheckup.domain.model

data class Character(
    val id: Int,
    val name: String,
    val image: String,
    val species: String,
    val status: String,
    val origin: String,
    val type: String?,
    val created: String
)
