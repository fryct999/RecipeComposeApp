package ru.fryct999.recipecomposeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.ui.categories.CategoriesScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.fryct999.recipecomposeapp.ui.navigation.BottomNavigation
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesApp() {
    RecipeComposeAppTheme {
        var currentScreenId by remember { mutableStateOf(ScreenId.CATEGORIES) }

        Scaffold(
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = { currentScreenId = ScreenId.CATEGORIES },
                    onFavoriteClick = { currentScreenId = ScreenId.FAVORITES },
                )
            },
        ) { paddingValues ->
            CategoriesScreen(contentPadding = paddingValues)
            when (currentScreenId) {
                ScreenId.CATEGORIES -> {
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("Экран категории")
                    }
                }

                ScreenId.FAVORITES -> {
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("Экран избранное")
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun RecipesAppPreview() {
    RecipesApp()
}