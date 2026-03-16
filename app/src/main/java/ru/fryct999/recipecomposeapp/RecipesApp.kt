package ru.fryct999.recipecomposeapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.ui.categories.CategoriesScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.fryct999.recipecomposeapp.ui.favorites.FavoritesScreen
import ru.fryct999.recipecomposeapp.ui.navigation.BottomNavigation
import ru.fryct999.recipecomposeapp.ui.recipes.RecipeItem
import ru.fryct999.recipecomposeapp.ui.recipes.RecipesScreen
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding10
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
            when (currentScreenId) {
                ScreenId.CATEGORIES -> {
                    CategoriesScreen(contentPadding = paddingValues)
                }

                ScreenId.FAVORITES -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        FavoritesScreen(contentPadding = paddingValues)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding10),
                        ) {
                            RecipeItem(
                                text = "СПИСОК РЕЦЕПТОВ",
                                contentDescription = "Страница списка рецептов",
                                painter = painterResource(id = R.drawable.img_ervar2),
                                { currentScreenId = ScreenId.RECIPES }
                            )
                        }
                    }
                }

                ScreenId.RECIPES -> {
                    RecipesScreen(contentPadding = paddingValues)
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