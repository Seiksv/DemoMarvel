package com.example.demomarvel.ui.share.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun CustomScaffolding(navigationController: NavHostController, backRoute: String? = null, isVisibleBackButton: Boolean = true, title :String? = null, content: @Composable ColumnScope.() -> Unit) {
    Scaffold(
        topBar = {
            CustomTopAppBar(navigationController, backRoute, isVisibleBackButton, title = title)
        },

    )
    { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding),
            content = content
        )
    }
}