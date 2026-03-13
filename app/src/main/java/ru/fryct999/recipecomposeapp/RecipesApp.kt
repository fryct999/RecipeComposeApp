package ru.fryct999.recipecomposeapp

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.ui.categories.CategoriesScreen
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesApp() {
    RecipeComposeAppTheme {
        Scaffold { paddingValues ->
            CategoriesScreen(contentPadding = paddingValues)
        }
    }
}

@Preview
@Composable
fun RecipesAppPreview() {
    RecipesApp()
}