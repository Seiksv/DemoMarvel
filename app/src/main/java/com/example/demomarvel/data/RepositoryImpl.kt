package com.example.demomarvel.data

import android.util.Log
import com.example.demomarvel.data.network.MarvelApiService
import com.example.demomarvel.domain.Repository
import com.example.demomarvel.domain.model.MarvelMultiCharacterModel
import com.example.demomarvel.data.utill.Constants
import com.example.demomarvel.data.utill.Md5HashGenerator
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: MarvelApiService) : Repository {

    override suspend fun getAllCharacters(offset: Int, characterNameQuery: String): List<MarvelMultiCharacterModel>? {
        val hash = Md5HashGenerator().generateHash( Constants.PRIVATE_KEY, Constants.PUBLIC_KEY)

        runCatching {
            if (characterNameQuery.isBlank() || characterNameQuery.isEmpty())
                apiService.getAllChars(
                    ts = Md5HashGenerator().getCurrentTimestamp().toString(),
                    apiKey = Constants.PUBLIC_KEY,
                    hash = hash,
                    offset = offset.toString(),
                    limit = "20",
                )
            else {
                apiService.getAllCharsWithName(
                    ts = Md5HashGenerator().getCurrentTimestamp().toString(),
                    apiKey = Constants.PUBLIC_KEY,
                    hash = hash,
                    offset = offset.toString(),
                    name = characterNameQuery,
                    limit = "20",
                )
            }
        }
            .onSuccess {
                Log.i("RepositoryImpl", "Success: ${it.body()}")
                return it.body()?.toDomain()
            }
            .onFailure { Log.i("RepositoryImpl", "Error: ${it.message}") }

        return null
    }
}
