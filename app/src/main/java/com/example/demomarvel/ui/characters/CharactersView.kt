package com.example.demomarvel.ui.characters

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.demomarvel.data.utill.AppScreens
import com.example.demomarvel.ui.share.component.CustomScaffolding
import com.example.demomarvel.ui.share.theme.DemoMarvelTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.demomarvel.data.enums.LoadingState
import com.example.demomarvel.data.extension.reachedBottom
import com.example.demomarvel.data.utill.SharedPreferencesSingleton
import com.example.demomarvel.domain.model.MarvelMultiCharacterModel
import com.example.demomarvel.ui.selectedItemGlobal
import com.example.demomarvel.ui.share.component.CustomProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


var characterSelected = 0
var firstLoading = false

@Composable
fun CharactersView(
    navigationController: NavHostController,
    charactersViewModel: CharactersViewModel
) {
    var selectedItem by remember { mutableIntStateOf(selectedItemGlobal) }
    val items = listOf("Characters", "Favs" )

    DemoMarvelTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Column {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)){
                    when(selectedItem) {
                        0 -> CharacterSection(navigationController, charactersViewModel)
                        1 -> FavsCharacters(navigationController, charactersViewModel)
                    }
                }

                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
                            label = { Text(item) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index
                                selectedItemGlobal = index}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterSection(navigationController: NavHostController, charactersViewModel: CharactersViewModel) {

    val isLoading: LoadingState by charactersViewModel.loadingState.observeAsState(LoadingState.SUCCESS)
    val characterData: List<MarvelMultiCharacterModel>? by charactersViewModel.characterData.observeAsState(
        emptyList()
    )
    val offset: Int by charactersViewModel.offset.observeAsState(0)
    val searchQuery: String by charactersViewModel.characterNameQuery.observeAsState("")
    var debounceJob: Job? = null
    val filteredCharacterList =
        characterData?.filter { it.name.contains(searchQuery, ignoreCase = true) }
    val uniqueIds = filteredCharacterList?.map { it.id }?.toSet()
    val allIdsAreUnique = uniqueIds?.size == filteredCharacterList?.size
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        if (searchQuery.isNullOrEmpty() && characterSelected == 0 && !firstLoading) {
            charactersViewModel.fetchCharacters()
            firstLoading = true
        }
    }

    CustomScaffolding(
        navigationController = navigationController,
        isVisibleBackButton = false,
        title = "Marvel"
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {

            TextField(
                value = searchQuery,
                onValueChange = { newValue ->
                    charactersViewModel.setCharacterNameQueryData(newValue)
                    val filteredCharacterListP = characterData?.filter {
                        it.name.contains(
                            searchQuery,
                            ignoreCase = true
                        )
                    }

                    debounceJob?.cancel()
                    debounceJob = CoroutineScope(Dispatchers.Main).launch {
                        delay(1000L) // wait 500ms for the user to stop typing
                        if (filteredCharacterListP.isNullOrEmpty() && searchQuery.isNotEmpty()) {
                            charactersViewModel.fetchCharacters()
                        }

                    }
                },
                label = { Text("Search") },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search Icon"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        debounceJob?.cancel()
                        charactersViewModel.setCharacterNameQueryData("")
                        focusManager.clearFocus()
                    }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear Search")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )

            if (allIdsAreUnique) {
                CharactersList(
                    characterList = filteredCharacterList,
                    loading = false,
                    loadMore = {
                        if (searchQuery.isEmpty()) {
                            charactersViewModel.setOffset(offset + 20)
                            charactersViewModel.fetchCharacters()
                            Log.i("CharactersView", "Load more")
                        }
                    },
                    navigationController = navigationController
                )
            }
        }
        CustomProgressBar(isLoading)
    }
}

@Composable
private fun CharactersList(
    characterList: List<MarvelMultiCharacterModel>?,
    loading: Boolean,
    loadMore: () -> Unit,
    navigationController: NavHostController
) {

    EndlessLazyColumn(
        loading = loading,
        items = characterList.let { it ?: emptyList() },
        itemKey = { character: MarvelMultiCharacterModel -> character.id },
        itemContent = { character: MarvelMultiCharacterModel ->
            CharacterCard(
                character = character,
                onClick = {
                    SharedPreferencesSingleton.saveCharacter(character)
                    characterSelected = character.id
                    navigationController.navigate(AppScreens.DetailCharacter.toString() + "/${character.id}")
                })
            Spacer(modifier = Modifier.height(8.dp))
        },
        loadingItem = { Text(text = "Loading") }, // loading card composable
        loadMore = loadMore
    )
}

@Composable
internal fun <T> EndlessLazyColumn(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    listState: LazyListState = rememberLazyListState(),
    items: List<T>,
    itemKey: (T) -> Any,
    itemContent: @Composable (T) -> Unit,
    loadingItem: @Composable () -> Unit,
    loadMore: () -> Unit
) {

    val reachedBottom: Boolean by remember { derivedStateOf { listState.reachedBottom() } }

    // load more if scrolled to bottom
    LaunchedEffect(reachedBottom) {
        if (reachedBottom && !loading) loadMore()
    }
    LazyColumn(modifier = modifier, state = listState) {
        itemsIndexed(items.chunked(2)) { _, rowItems ->
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                for (item in rowItems) {
                    Box(modifier = Modifier.weight(1f)) {
                        itemContent(item)
                    }
                }
            }
        }
        if (loading) {
            item {
                loadingItem()
            }
        }
    }
}


@Composable
fun FavsCharacters(
    navigationController: NavHostController,
    charactersViewModel: CharactersViewModel
){
    var filteredCharacterList = charactersViewModel.getFavCharacters()

    CustomScaffolding(
        navigationController = navigationController,
        isVisibleBackButton = false,
        title = "Marvel"
    ) {
        Log.i("CharactersView", "Fav Characters: $filteredCharacterList")
        CharactersList(
            characterList = filteredCharacterList,
            loading = false,
            loadMore = {
                Log.i("CharactersView", "Load more")
            },
            navigationController = navigationController
        )
    }


}
