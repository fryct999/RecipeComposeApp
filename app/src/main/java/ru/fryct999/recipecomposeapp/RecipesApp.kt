package ru.fryct999.recipecomposeapp

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import ru.fryct999.recipecomposeapp.core.utils.FavoriteDataStoreManager
import ru.fryct999.recipecomposeapp.navigation.Destination
import ru.fryct999.recipecomposeapp.ui.navigation.BottomNavigation
import ru.fryct999.recipecomposeapp.navigation.AppNavHost
import ru.fryct999.recipecomposeapp.navigation.Constants.DEEP_LINK_SCHEME
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesApp(
    deepLinkIntent: Intent?
) {
    RecipeComposeAppTheme {
        val navController = rememberNavController()
        val context = LocalContext.current
        val favoriteDataStoreManager = remember { FavoriteDataStoreManager(context) }

        LaunchedEffect(deepLinkIntent) {
            deepLinkIntent?.data?.let { uri ->
                val recipeId: Int? = when (uri.scheme) {
                    DEEP_LINK_SCHEME ->
                        if (uri.host == "recipe") uri.pathSegments.getOrNull(0)
                            ?.toIntOrNull() else null

                    "https", "http" ->
                        if (uri.pathSegments.getOrNull(0) == "recipe") uri.pathSegments.getOrNull(1)
                            ?.toIntOrNull() else null

                    else -> null
                }

                if (recipeId != null) {
                    delay(100)
                    navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
                }
            }
        }

        Scaffold(
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = {
                        navController.navigate(Destination.Categories.route)
                    },
                    onFavoriteClick = {
                        navController.navigate(Destination.Favorite.route)
                    },
                    favoriteDataStoreManager = favoriteDataStoreManager,
                )
            },
        ) { paddingValues ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}

@Preview
@Composable
fun RecipesAppPreview() {
    RecipesApp(null)
}