package com.saika.multiversecheckup.domain.usecase

import com.saika.multiversecheckup.domain.model.Character
import com.saika.multiversecheckup.domain.repository.CharacterRepository
import com.saika.multiversecheckup.domain.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewUseCaseTest {

    private val repository = mockk<CharacterRepository>()
    private lateinit var useCase: CharactersViewUseCase

    @Before
    fun setUp() {
        useCase = CharactersViewUseCase(repository)
    }

    @Test
    fun `invoke returns Resource Success`() = runTest {
        val expectedCharacters = listOf(
            Character(1, "Rick", "img", "Human", "Alive", "Earth", null, "2017-11-30T14:28:54.596Z")
        )
        coEvery { repository.searchCharacters("Rick") } returns Resource.Success(expectedCharacters)

        val result = useCase("Rick")

        assertTrue(result is Resource.Success)
        assertEquals(1, (result as Resource.Success).data.size)
    }
}