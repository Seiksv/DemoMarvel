package com.example.demomarvel.ui.charactersDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demomarvel.data.enums.LoadingState
import com.example.demomarvel.data.utill.SharedPreferencesSingleton
import com.example.demomarvel.domain.model.MarvelMultiCharacterModel
import com.example.demomarvel.domain.usecase.GetCharactersUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CharactersDetailsViewModel @Inject constructor(private val getCharactersUserCase: GetCharactersUserCase) :
    ViewModel() {
    private val _characterData = MutableLiveData<MarvelMultiCharacterModel>()
    var characterData: LiveData<MarvelMultiCharacterModel> = _characterData



    fun fetchCharacters() {
       _characterData.value = SharedPreferencesSingleton.getCharacter()
    }

    fun setFavCharacter(character: MarvelMultiCharacterModel) {
        val favCharacters = SharedPreferencesSingleton.getFavCharacters().toMutableList()
        favCharacters.add(character)
        SharedPreferencesSingleton.saveFavCharacters(favCharacters)
    }

    fun getFavCharacters(): List<MarvelMultiCharacterModel> {
        return SharedPreferencesSingleton.getFavCharacters()
    }
    fun deleteFavCharacter(character: MarvelMultiCharacterModel) {
        val favCharacters = SharedPreferencesSingleton.getFavCharacters().toMutableList()
        favCharacters.remove(character)
        SharedPreferencesSingleton.saveFavCharacters(favCharacters)
    }

    fun isCharacterFav(character: MarvelMultiCharacterModel): Boolean {
        val favCharacters = SharedPreferencesSingleton.getFavCharacters()
        return favCharacters.contains(character)
    }
}