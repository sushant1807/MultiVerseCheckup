package com.saika.multiversecheckup.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saika.multiversecheckup.domain.model.Character
import com.saika.multiversecheckup.domain.usecase.CharactersViewUseCase
import com.saika.multiversecheckup.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val searchImagesUseCase: CharactersViewUseCase
): ViewModel() {

    val uiState = MutableStateFlow<Resource<List<Character>>>(Resource.Success(emptyList()))

    fun searchImages(query: String){
        viewModelScope.launch {
            uiState.value = Resource.Loading

            val result = searchImagesUseCase(query)

            uiState.value = result
        }
    }
}