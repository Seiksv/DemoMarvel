package com.example.demomarvel.data.utill

import com.example.demomarvel.domain.model.MarvelMultiCharacterModel

sealed class AppScreens(val route: String, val character: MarvelMultiCharacterModel? = null) {
    data object LoginScreen: AppScreens(Screens.Login.toString())
    data object AllCharacters: AppScreens(Screens.AllCharacters.toString())
    data object DetailCharacter: AppScreens(Screens.DetailCharacter.toString() + "/{characterId}", character = null)

    enum class Screens() {
        Login,
        AllCharacters,
        DetailCharacter;
    }
}