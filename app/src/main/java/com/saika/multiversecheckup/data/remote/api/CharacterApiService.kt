package com.saika.multiversecheckup.data.remote.api

import com.saika.multiversecheckup.data.remote.dto.CharacterDto
import com.saika.multiversecheckup.data.remote.dto.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApiService {

    @GET("character")
    suspend fun searchCharacters(@Query("name") name: String): CharacterResponseDto

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterDto

}