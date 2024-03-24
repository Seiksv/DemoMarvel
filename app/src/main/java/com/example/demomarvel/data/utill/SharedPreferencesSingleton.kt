package com.example.demomarvel.data.utill

import android.content.Context
import android.content.SharedPreferences
import com.example.demomarvel.domain.model.MarvelMultiCharacterModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesSingleton {
    private const val PREFERENCE_NAME = "MyPrefs"
    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun getSharedPreferences(): SharedPreferences {
        if (!::sharedPreferences.isInitialized) {
            throw UninitializedPropertyAccessException("SharedPreferencesSingleton must be initialized")
        }
        return sharedPreferences
    }


    fun saveCharacter(characterModel: MarvelMultiCharacterModel) {
        val sharedPreferences = SharedPreferencesSingleton.getSharedPreferences()

        // Convert JSON string back to MarvelMultiCharacterModel
        val gson = Gson()
        val characterJson = gson.toJson(characterModel)
        sharedPreferences.edit().putString("character_temp", characterJson).apply()
    }

    fun getCharacter(): MarvelMultiCharacterModel? {
        val sharedPreferences = SharedPreferencesSingleton.getSharedPreferences()
        val gson = Gson()
        val characterJson = sharedPreferences.getString("character_temp", null)
        return gson.fromJson(characterJson, MarvelMultiCharacterModel::class.java)
    }

    fun deleteCharacter() {
        val sharedPreferences = SharedPreferencesSingleton.getSharedPreferences()
        sharedPreferences.edit().remove("character_temp").apply()
    }

    fun saveFavCharacters(characters: List<MarvelMultiCharacterModel>) {
        val gson = Gson()
        val charactersJson = gson.toJson(characters)
        sharedPreferences.edit().putString("favCharacters", charactersJson).apply()
    }

    fun getFavCharacters(): List<MarvelMultiCharacterModel> {

        val charactersJson = sharedPreferences.getString("favCharacters", null)
        return if (charactersJson != null) {
            val gson = Gson()
            val type = object : TypeToken<List<MarvelMultiCharacterModel>>() {}.type
            gson.fromJson(charactersJson, type)
        } else {
            emptyList()
        }
    }
}