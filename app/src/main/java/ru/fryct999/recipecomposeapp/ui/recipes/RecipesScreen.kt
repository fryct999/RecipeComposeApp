package ru.fryct999.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.core.ui.ScreenHeader
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    ScreenHeader(
        painter = painterResource(id = R.drawable.bcg_recipes_list),
        contentDescription = "Раздел рецепты",
        text = "РЕЦЕПТЫ",
        modifier = Modifier.padding(top = contentPadding.calculateTopPadding())
    )

    Box(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text("Скоро здесь будет список рецептов")
    }
}

@Preview
@Composable
fun RecipesScreenPreview() {
    RecipeComposeAppTheme {
        RecipesScreen(PaddingValues())
    }
}