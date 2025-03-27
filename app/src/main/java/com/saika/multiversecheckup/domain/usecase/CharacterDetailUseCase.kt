package com.saika.multiversecheckup.domain.usecase

import com.saika.multiversecheckup.domain.model.Character
import com.saika.multiversecheckup.domain.repository.CharacterRepository

class CharacterDetailUseCase(private val repository: CharacterRepository) {

    suspend operator fun invoke(id: Int): Character {
        return repository.getCharacterById(id)
    }
}