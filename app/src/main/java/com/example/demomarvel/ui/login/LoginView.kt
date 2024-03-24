package com.example.demomarvel.ui.login

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.demomarvel.R
import com.example.demomarvel.data.utill.AppScreens
import com.example.demomarvel.ui.charactersDetails.CharacterBanner
import com.example.demomarvel.ui.charactersDetails.CharactersDetailsViewModel

@Composable
fun LoginView(
    navigationController: NavHostController,
    charactersDetailsViewModel: CharactersDetailsViewModel,
    s: String
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        CharacterBanner(path = "https://img.etimg.com/thumb/width-1200,height-900,imgsize-5622,resizemode-75,msid-101288749/news/international/us/marvel-cinematic-universe-titles-see-the-right-order-to-watch-these-movies-series.jpg")

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(100.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navigationController.navigate(AppScreens.AllCharacters.route) }) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Forgot password?",
                modifier = Modifier.clickable { /* Handle forgot password action */ }
            )
        }

    }
}