package com.example.demomarvel.domain.model

data class MarvelMultiCharacterModel(
    val id: Int,
    val name: String,
    val path: String,
    val description: String,
    val modified: String,
    val resourceURI: String,
    val comics: MarvelComics,
    val series: MarvelSeries,
    val stories: MarvelStories,
    val events: MarvelEvents,
    val urls: List<String>,
    val isFavorite: Boolean
)

data class MarvelItem(
    val name: String,
    val url: String
)
data class MarvelComics(
    val available: Int,
    val collectionURI: String,
    val items: List<MarvelItem>,
)

data class MarvelSeries(
    val available: Int,
    val collectionURI: String,
    val items: List<MarvelItem>,
)

data class MarvelStories(
    val available: Int,
    val collectionURI: String,
    val items: List<MarvelItem>,
)

data class MarvelEvents(
    val available: Int,
    val collectionURI: String,
    val items: List<MarvelItem>,
)

