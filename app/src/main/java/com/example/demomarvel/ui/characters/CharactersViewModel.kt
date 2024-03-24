package com.example.demomarvel.ui.characters

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
class CharactersViewModel @Inject constructor(private val getCharactersUserCase: GetCharactersUserCase) :
    ViewModel() {
    private val _characterData = MutableLiveData< List<MarvelMultiCharacterModel>?>()
    var characterData: LiveData< List<MarvelMultiCharacterModel>?> = _characterData

    private val _offset = MutableLiveData(0)
    val offset: LiveData<Int> = _offset

    private val _units = MutableLiveData("imperial")
    val units: LiveData<String> = _units

    private val _loadingState = MutableLiveData(LoadingState.SUCCESS)
    var loadingState: LiveData<LoadingState> = _loadingState

    private val _characterNameQuery = MutableLiveData("")
    val characterNameQuery: LiveData<String> = _characterNameQuery

    fun fetchCharacters() {
        if (_loadingState.value != LoadingState.LOADING)
        {
            _loadingState.value = LoadingState.LOADING
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    getCharactersUserCase(_offset.value!!, characterNameQuery.value!!)
                }
                if (response != null){
                    Log.i("CharactersViewModel", "Success: ${response}")
                    _loadingState.value = LoadingState.SUCCESS
                    if (_characterData.value == null)
                        _characterData.value = response
                    else
                        _characterData.value = _characterData.value?.plus(response)
                } else {
                    Log.i("CharactersViewModel", "Error: ${response}")
                    _loadingState.value = LoadingState.ERROR
                }
            }
        }

    }

    fun setOffset(offset: Int) {
        _offset.value = offset
    }

    fun cleanCharacterNameQueryData() {
        _characterNameQuery.value = ""
    }

    fun setCharacterNameQueryData(query: String){
        _characterNameQuery.value = query
    }

    fun getFavCharacters(): List<MarvelMultiCharacterModel> {
        return SharedPreferencesSingleton.getFavCharacters()
    }
}