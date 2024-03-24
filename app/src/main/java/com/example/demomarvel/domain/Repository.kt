package com.example.demomarvel.domain

import com.example.demomarvel.domain.model.MarvelMultiCharacterModel

interface Repository {
    suspend fun getAllCharacters(offset: Int, characterNameQuery: String): List<MarvelMultiCharacterModel>?
}