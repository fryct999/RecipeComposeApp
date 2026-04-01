package ru.fryct999.recipecomposeapp.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.core.ui.ScreenHeader
import ru.fryct999.recipecomposeapp.ui.recipes.model.IngredientUiModel
import ru.fryct999.recipecomposeapp.ui.recipes.model.RecipeUiModel
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        ScreenHeader(
            painter = painterResource(id = R.drawable.img_ervar2),
            contentDescription = "Описание рецепта ${recipe.title}",
            text = recipe.title,
        )
    }
}

@Preview
@Composable
fun RecipeDetailsPreview() {
    RecipeComposeAppTheme {
        RecipeDetailsScreen(
            modifier = Modifier,
            recipe = RecipeUiModel(
                id = 1,
                title = "Test recipe",
                imageUrl = "img_ervar2",
                ingredients = listOf(
                    IngredientUiModel("test ing 1", "1", "1"),
                    IngredientUiModel("test ing 2", "2", "2"),
                    IngredientUiModel("test ing 3", "3", "3"),
                ),
                method = listOf(
                    "Первый шаг",
                    "Второй шаг",
                    "Ну и третий шаг",
                )
            )
        )
    }
}