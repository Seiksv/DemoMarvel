package com.example.demomarvel.ui.charactersDetails

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest


@Composable
fun CharacterBanner(path: String) {
 Log.i("CharacterBanner", "CharacterBanner: $path")
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = path)
            .apply(block = fun ImageRequest.Builder.() {
                listener(
                    onStart = { Log.d("Coil details", "Started image request.") },
                    onSuccess = { _, response ->
                        Log.d(
                            "Coil Details",
                            "Image request succeeded. " + response.dataSource
                        )
                    },
                    onError = { _, throwable ->
                        Log.e(
                            "Coil Details",
                            "Image request failed." + throwable.throwable.toString(),
                        )
                    },
                    onCancel = { Log.d("Coil Details", "Image request cancelled.") }
                )
            }).build()
    )


    Column {
        Image(
            painter = painter,
            contentDescription = "Character Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .align(Alignment.CenterHorizontally)
                .clip(ShapeDefaults.Large), // Clip the image with rounded corners
            contentScale = ContentScale.Crop
        )
    }

}