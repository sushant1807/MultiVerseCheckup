package com.saika.multiversecheckup.di

import com.saika.multiversecheckup.BuildConfig
import com.saika.multiversecheckup.data.remote.api.CharacterApiService
import com.saika.multiversecheckup.data.repository.CharacterRepositoryImpl
import com.saika.multiversecheckup.domain.repository.CharacterRepository
import com.saika.multiversecheckup.domain.usecase.CharacterDetailUseCase
import com.saika.multiversecheckup.domain.usecase.CharactersViewUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideCharacterApiService(): CharacterApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharacterApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(apiService: CharacterApiService): CharacterRepository {
        return CharacterRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCharacterUseCase(repository: CharacterRepository): CharactersViewUseCase {
        return CharactersViewUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCharacterDetailUseCase(repository: CharacterRepository): CharacterDetailUseCase {
        return CharacterDetailUseCase(repository)
    }
}