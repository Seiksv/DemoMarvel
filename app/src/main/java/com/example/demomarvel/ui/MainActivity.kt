package com.example.demomarvel.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.example.demomarvel.data.utill.AppScreens
import com.example.demomarvel.data.utill.SharedPreferencesSingleton
import com.example.demomarvel.ui.characters.CharactersView
import com.example.demomarvel.ui.characters.CharactersViewModel
import com.example.demomarvel.ui.charactersDetails.CharactersDetailView
import com.example.demomarvel.ui.charactersDetails.CharactersDetailsViewModel
import com.example.demomarvel.ui.login.LoginView
import com.example.demomarvel.ui.share.theme.DemoMarvelTheme
import dagger.hilt.android.AndroidEntryPoint

var selectedItemGlobal = 0

@AndroidEntryPoint
class MainActivity : ComponentActivity(), ImageLoaderFactory {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        SharedPreferencesSingleton.initialize(this)

        val charactersViewModel: CharactersViewModel by viewModels()
        val charactersDetailsViewModel: CharactersDetailsViewModel by viewModels()


        setContent {
            val navigationController = rememberNavController()
            val navBackStackEntry by navigationController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            DemoMarvelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navigationController,
                        startDestination = AppScreens.LoginScreen.route.toString(), //AppScreens.LoginScreen.route.toString()
                    ) {
                        composable(AppScreens.AllCharacters.route.toString()) {
                            CharactersView(
                                navigationController,
                                charactersViewModel
                            )
                        }
                        composable(AppScreens.DetailCharacter.route) { backStackEntry ->
                            val characterId = backStackEntry.arguments?.getString("characterId")
                            CharactersDetailView(navigationController, charactersDetailsViewModel, characterId ?: "")
                        }
                        composable(AppScreens.LoginScreen.route) { backStackEntry ->
                            val characterId = backStackEntry.arguments?.getString("characterId")
                            LoginView(navigationController, charactersDetailsViewModel, characterId ?: "")
                        }
                    }
                }
            }
        }
    }
    override fun newImageLoader(): ImageLoader {

        return ImageLoader(this)
            .newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache{
                MemoryCache.Builder(this)
                    .maxSizePercent(0.1)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache{
                DiskCache.Builder()
                    .maxSizePercent(0.1)
                    .directory(cacheDir)
                    .build()

            }
            .logger(DebugLogger())
            .build()


    }
}
