package com.saika.multiversecheckup.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saika.multiversecheckup.domain.usecase.CharacterDetailUseCase
import com.saika.multiversecheckup.domain.util.Resource
import com.saika.multiversecheckup.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetail: CharacterDetailUseCase
) : ViewModel() {

    var uiState by mutableStateOf<Resource<Character>>(Resource.Loading)
        private set

    fun loadCharacter(id: Int) {
        viewModelScope.launch {
            uiState = Resource.Loading
            try {
                val result = getCharacterDetail(id)
                uiState = Resource.Success(result)
            } catch (e: Exception) {
                uiState = Resource.Error(e.message ?: "Failed to load character")
            }
        }
    }

}