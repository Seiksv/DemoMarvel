package com.example.demomarvel.ui.share.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.demomarvel.data.enums.LoadingState
import kotlinx.coroutines.delay

@Composable
fun CustomProgressBar(isLoading: LoadingState) {

    var currentProgress by remember { mutableStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(true) }

    val animatedProgress by animateFloatAsState(
        targetValue = if (isLoading == LoadingState.SUCCESS) 1f else 0f
    )

    when(isLoading) {
        LoadingState.LOADING -> {
            isVisible = true
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
            )
        }
        LoadingState.SUCCESS -> {
            loading = false
            currentProgress = animatedProgress

            if (isVisible) {
                LinearProgressIndicator(
                    progress = {
                        currentProgress
                    },
                    modifier = Modifier.fillMaxWidth(),
                    trackColor = MaterialTheme.colorScheme.primary,
                )
            }

            // Start a coroutine to wait for 2 seconds and then hide the progress bar
            LaunchedEffect(key1 = isLoading) {
                delay(2000)
                isVisible = false
            }

        }
        LoadingState.ERROR -> {
            isVisible = true
            loading = false
            currentProgress = animatedProgress

            LinearProgressIndicator(
                progress = {
                    currentProgress
                },
                modifier = Modifier.fillMaxWidth(),
                trackColor = MaterialTheme.colorScheme.onError,
            )
        }
    }
}