package com.saika.multiversecheckup.domain.usecase

import com.saika.multiversecheckup.domain.model.Character
import com.saika.multiversecheckup.domain.repository.CharacterRepository
import com.saika.multiversecheckup.domain.util.Resource

class CharactersViewUseCase(private val repository: CharacterRepository) {

    suspend operator fun invoke(query: String): Resource<List<Character>> {
        return repository.searchCharacters(query)
    }

}