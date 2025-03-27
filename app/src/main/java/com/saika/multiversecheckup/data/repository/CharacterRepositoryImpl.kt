package com.saika.multiversecheckup.data.repository

import com.saika.multiversecheckup.data.remote.api.CharacterApiService
import com.saika.multiversecheckup.data.remote.dto.toDomain
import com.saika.multiversecheckup.domain.repository.CharacterRepository
import com.saika.multiversecheckup.domain.model.Character
import com.saika.multiversecheckup.domain.util.Resource


class CharacterRepositoryImpl(private val apiService: CharacterApiService) : CharacterRepository {
    override suspend fun searchCharacters(query: String): Resource<List<Character>> {
        return try {
            val response = apiService.searchCharacters(query).results.map { it.toDomain() }
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error("Failed to load images: ${e.localizedMessage}")
        }
    }

    override suspend fun getCharacterById(id: Int): Character {
        return apiService.getCharacterById(id).toDomain()
    }
}