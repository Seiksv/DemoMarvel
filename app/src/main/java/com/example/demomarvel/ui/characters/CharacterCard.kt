package com.example.demomarvel.ui.characters

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.demomarvel.R
import com.example.demomarvel.domain.model.MarvelMultiCharacterModel

@Composable
fun CharacterCard(character: MarvelMultiCharacterModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(8.dp),
        onClick = onClick
    ) {
        Column {
            Log.i("CharacterCard", "CharacterCard: ${character.name} image url: ${character.path}")

            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = character.path)
                    .apply(block = fun ImageRequest.Builder.() {
                        listener(
                            onStart = { Log.d("Coil", "Started image request.") },
                            onSuccess = { _, response ->
                                Log.d(
                                    "Coil",
                                    "Image request succeeded. " + response.dataSource
                                )
                            },
                            onError = { _, throwable ->
                                Log.e(
                                    "Coil",
                                    "Image request failed." + throwable.throwable.toString(),
                                )
                            },
                            onCancel = { Log.d("Coil", "Image request cancelled.") }
                        )
                    }).build()
            )


            Image(
                painter = painter,
                contentDescription = "Character Image",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(ShapeDefaults.Large), // Clip the image with rounded corners
                contentScale = ContentScale.Crop
            )
            Text(
                character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 8.dp, 0.dp, 0.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                character.id.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 0.dp, 0.dp, 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}