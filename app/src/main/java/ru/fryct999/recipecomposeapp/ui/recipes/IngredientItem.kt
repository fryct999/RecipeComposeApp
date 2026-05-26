package ru.fryct999.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.ui.recipes.model.IngredientUiModel
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun IngredientItem(
    ingredient: IngredientUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = ingredient.name.uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = "${ingredient.amount} ${ingredient.unitOfMeasure}".uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview
@Composable
fun IngredientItemPreview() {
    RecipeComposeAppTheme {
        IngredientItem(
            IngredientUiModel(
                name = "Test name",
                amount = "123",
                unitOfMeasure = "шт"
            ),
            modifier = Modifier,
        )
    }
}