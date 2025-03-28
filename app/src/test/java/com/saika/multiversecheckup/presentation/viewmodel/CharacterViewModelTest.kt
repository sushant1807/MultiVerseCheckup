package com.saika.multiversecheckup.presentation.viewmodel

import com.saika.multiversecheckup.domain.model.Character
import com.saika.multiversecheckup.domain.usecase.CharactersViewUseCase
import com.saika.multiversecheckup.domain.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterViewModelTest {

    private val useCase = mockk<CharactersViewUseCase>()
    private lateinit var viewModel: CharacterViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // for viewModelScope
        viewModel = CharacterViewModel(useCase, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchImages updates uiState to Loading then Success`() = runTest {
        // Arrange
        val characters = listOf(
            Character(1, "Morty", "img", "Human", "Alive", "Earth", "", "2017-11-30T14:28:54.596Z")
        )
        coEvery { useCase("Morty") } returns Resource.Success(characters)

        // Act
        viewModel.searchImages("Morty")
        advanceUntilIdle() // 🚨 required to execute coroutine updates

        // Assert
        val result = viewModel.uiState.value
        assertTrue(result is Resource.Success)
        assertEquals(1, (result as Resource.Success).data.size)
        assertEquals("Morty", result.data[0].name)
    }
}
