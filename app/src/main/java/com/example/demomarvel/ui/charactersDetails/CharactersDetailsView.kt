package com.example.demomarvel.ui.charactersDetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.demomarvel.data.utill.SharedPreferencesSingleton
import com.example.demomarvel.domain.model.MarvelComics
import com.example.demomarvel.domain.model.MarvelEvents
import com.example.demomarvel.domain.model.MarvelMultiCharacterModel
import com.example.demomarvel.domain.model.MarvelSeries
import com.example.demomarvel.domain.model.MarvelStories
import com.example.demomarvel.ui.share.component.CustomScaffolding
import com.example.demomarvel.ui.share.theme.DemoMarvelTheme


@Composable
fun CharactersDetailView(
    navigationController: NavHostController,
    charactersDetailsViewModel: CharactersDetailsViewModel,
    characterId: String
) {
    Log.i("CharactersDetailView", "Received characterId: $characterId")
    Log.i("CharactersDetailView", SharedPreferencesSingleton.getCharacter().toString())
    val character: MarvelMultiCharacterModel? by charactersDetailsViewModel.characterData.observeAsState(
        initial = null
    )
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        charactersDetailsViewModel.fetchCharacters()
    }

    DemoMarvelTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            CustomScaffolding(
                navigationController = navigationController,
                isVisibleBackButton = true,
                title = character?.name ?: "Character Details"
            ) {
                character?.let {
                    CharacterBanner(path = it.path)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())

                ) {



                    character?.let {
                        FavoriteButton(
                            character = it,
                            charactersDetailsViewModel = charactersDetailsViewModel
                        )
                    }


                    character?.description?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(0.dp, 10.dp)
                        )
                    }
                    character?.comics?.let { comics ->
                        comicsList(comics, context)
                    }

                    character?.series?.let { series ->
                        comicsList(series, context)

                    }
                    character?.stories?.let { stories ->
                        comicsList(stories, context)
                    }

                    character?.events?.let { events ->
                        comicsList(events, context)
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    color: Color = Color(0xffE91E63),
    character: MarvelMultiCharacterModel?,
    charactersDetailsViewModel: CharactersDetailsViewModel
) {


    var isClicked by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }
    var setFavorite by remember { mutableStateOf("Set Favorite") }

    if (isFavorite){
        setFavorite = "Remove Favorite"
    } else {
        setFavorite = "Set Favorite"
    }

    IconToggleButton(
        modifier = Modifier.fillMaxWidth().padding(0.dp, 10.dp),
        checked = isFavorite,
        onCheckedChange = {
            isFavorite = !isFavorite
            isClicked = true
        }
    ) {
        Row {
            Text(text = setFavorite )
            Spacer(modifier = Modifier.size(10.dp))
            Icon(
                tint = color,
                modifier = modifier.graphicsLayer {
                    scaleX = 1.3f
                    scaleY = 1.3f
                },
                imageVector = if (isFavorite) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = null
            )
        }

    }

    if (isClicked){
        if (isFavorite) {
            if (charactersDetailsViewModel.getFavCharacters().contains(character!!)) {
                charactersDetailsViewModel.deleteFavCharacter(character!!)
            }
            charactersDetailsViewModel.setFavCharacter(character!!)


        } else {
            charactersDetailsViewModel.deleteFavCharacter(character!!)
        }
    }


}

@Composable
fun comicsList(comics: MarvelEvents, context: Context) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Events")
                Text(text = "Watch more", modifier = Modifier
                    .padding(0.dp, 0.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(comics.collectionURI))
                        context.startActivity(intent)
                    })
            }
            LazyRow {
                items(comics.items) { comic ->
                    Card(modifier = Modifier
                        .padding(4.dp, 5.dp)
                        .align(Alignment.CenterHorizontally),

                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(comic.url))
                            context.startActivity(intent)
                        })

                    {

                        Text(
                            text = comic.name,
                            modifier = Modifier
                                .padding(10.dp, 10.dp)
                                .size(140.dp, 100.dp)
                                .align(Alignment.CenterHorizontally)

                        )
                    }
                }
            }
        }
    }
}

@Composable
fun comicsList(comics: MarvelStories, context: Context) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Stories")
                Text(text = "Watch more", modifier = Modifier
                    .padding(0.dp, 0.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(comics.collectionURI))
                        context.startActivity(intent)
                    })
            }
            LazyRow {
                items(comics.items) { comic ->
                    Card(modifier = Modifier
                        .padding(4.dp, 5.dp)
                        .align(Alignment.CenterHorizontally),

                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(comic.url))
                            context.startActivity(intent)
                        })

                    {

                        Text(
                            text = comic.name,
                            modifier = Modifier
                                .padding(10.dp, 10.dp)
                                .size(140.dp, 100.dp)
                                .align(Alignment.CenterHorizontally)

                        )
                    }
                }
            }
        }
    }
}

@Composable
fun comicsList(comics: MarvelSeries, context: Context) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Series")
                Text(text = "Watch more", modifier = Modifier
                    .padding(0.dp, 0.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(comics.collectionURI))
                        context.startActivity(intent)
                    })
            }
            LazyRow {
                items(comics.items) { comic ->
                    Card(modifier = Modifier
                        .padding(4.dp, 5.dp)
                        .align(Alignment.CenterHorizontally),

                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(comic.url))
                            context.startActivity(intent)
                        })

                    {

                        Text(
                            text = comic.name,
                            modifier = Modifier
                                .padding(10.dp, 10.dp)
                                .size(140.dp, 100.dp)
                                .align(Alignment.CenterHorizontally)

                        )
                    }
                }
            }
        }
    }
}

@Composable
fun comicsList(comics: MarvelComics, context: Context) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Comics")
                Text(text = "Watch more", modifier = Modifier
                    .padding(0.dp, 0.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(comics.collectionURI))
                        context.startActivity(intent)
                    })
            }
            LazyRow {
                items(comics.items) { comic ->
                    Card(modifier = Modifier
                        .padding(4.dp, 5.dp)
                        .align(Alignment.CenterHorizontally),

                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(comic.url))
                            context.startActivity(intent)
                        })

                    {

                        Text(
                            text = comic.name,
                            modifier = Modifier
                                .padding(10.dp, 10.dp)
                                .size(140.dp, 100.dp)
                                .align(Alignment.CenterHorizontally)

                        )
                    }
                }
            }
        }
    }
}