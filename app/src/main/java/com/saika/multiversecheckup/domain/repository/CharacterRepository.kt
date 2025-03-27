package com.saika.multiversecheckup.domain.repository


import com.saika.multiversecheckup.domain.model.Character
import com.saika.multiversecheckup.domain.util.Resource

interface CharacterRepository {
    suspend fun searchCharacters(query: String): Resource<List<Character>>
    suspend fun getCharacterById(id: Int): Character
}