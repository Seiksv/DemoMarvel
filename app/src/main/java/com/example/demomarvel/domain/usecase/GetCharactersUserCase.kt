package com.example.demomarvel.domain.usecase

import com.example.demomarvel.domain.Repository
import javax.inject.Inject

class GetCharactersUserCase  @Inject constructor(private val repository: Repository) {

        suspend operator fun invoke(offset: Int, characterNameQuery: String) = repository.getAllCharacters(offset, characterNameQuery)

}