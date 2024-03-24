package com.example.demomarvel.data.network.response

import com.example.demomarvel.domain.model.MarvelComics
import com.example.demomarvel.domain.model.MarvelEvents
import com.example.demomarvel.domain.model.MarvelItem
import com.example.demomarvel.domain.model.MarvelMultiCharacterModel
import com.example.demomarvel.domain.model.MarvelSeries
import com.example.demomarvel.domain.model.MarvelStories
import com.google.gson.annotations.SerializedName

data class MarvelCharactersResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("copyright") val copyright: String,
    @SerializedName("attributionText") val attributionText: String,
    @SerializedName("attributionHTML") val attributionHTML: String,
    @SerializedName("etag") val etag: String,
    @SerializedName("data") val data: Data
) {
    fun toDomain(): List<MarvelMultiCharacterModel> {
        return data.results.map { result ->
            MarvelMultiCharacterModel(
                id = result.id,
                name = result.name,
                path = result.thumbnail.path.replace("http", "https")+ "." + result.thumbnail.extension,
                description = result.description,
                modified = result.modified,
                resourceURI = result.resourceURI,
                comics = MarvelComics(
                    available = result.comics.available,
                    collectionURI = result.comics.collectionURI,
                    items = result.comics.items.map { MarvelItem(name = it.name, url = it.resourceURI) }
                ),
                series = MarvelSeries(
                    available = result.series.available,
                    collectionURI = result.series.collectionURI,
                    items = result.series.items.map { MarvelItem(name = it.name, url = it.resourceURI) }
                ),
                stories = MarvelStories(
                    available = result.stories.available,
                    collectionURI = result.stories.collectionURI,
                    items = result.stories.items.map { MarvelItem(name = it.name, url = it.resourceURI) }
                ),
                events = MarvelEvents(
                    available = result.events.available,
                    collectionURI = result.events.collectionURI,
                    items = result.events.items.map { MarvelItem(name = it.name, url = it.resourceURI) }
                ),
                urls = result.urls.map { it.url },
                isFavorite = false
            )
        }
    }
}
data class Thumbnail(
    @SerializedName("path") val path: String,
    @SerializedName("extension") val extension: String
)
data class Item(
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("name") val name: String
)
data class Comics(
    @SerializedName("available") val available: Int,
    @SerializedName("collectionURI") val collectionURI: String,
    @SerializedName("items") val items: List<Item>,
)

data class Series(
    @SerializedName("available") val available: Int,
    @SerializedName("collectionURI") val collectionURI: String,
    @SerializedName("items") val items: List<Item>,
)

data class Stories(
    @SerializedName("available") val available: Int,
    @SerializedName("collectionURI") val collectionURI: String,
    @SerializedName("items") val items: List<Item>,
)

data class Events(
    @SerializedName("available") val available: Int,
    @SerializedName("collectionURI") val collectionURI: String,
    @SerializedName("items") val items: List<Item>,
)

data class Url(
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String
)

data class Result(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("modified") val modified: String,
    @SerializedName("thumbnail") val thumbnail: Thumbnail,
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("comics") val comics: Comics,
    @SerializedName("series") val series: Series,
    @SerializedName("stories") val stories: Stories,
    @SerializedName("events") val events: Events,
    @SerializedName("urls") val urls: List<Url>
)

data class Data(
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<Result>
)
