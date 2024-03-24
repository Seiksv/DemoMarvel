package com.example.demomarvel.ui.share.component

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.demomarvel.R
import androidx.compose.material3.Icon
import androidx.compose.material3.Text

import com.example.demomarvel.data.utill.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    navigationController: NavHostController,
    backRoute: String?,
    isVisibleBackButton: Boolean = true,
    title: String? = null
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.secondary,
        ),
        title = {

            val imageModifier = Modifier
                .padding(0.dp, 10.dp)
                .size(210.dp, 60.dp)
                .padding(0.dp)

            Text(text = title ?: "Marvel Characters")

        },

        navigationIcon = {
            if (isVisibleBackButton) {
                IconButton(onClick = {
                        navigationController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}