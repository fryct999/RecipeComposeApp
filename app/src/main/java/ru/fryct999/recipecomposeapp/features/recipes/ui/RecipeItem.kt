package ru.fryct999.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.core.ui.RecipeImage
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding8
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.recipeItemHeight
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.shadow
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.shapeDefault
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipeItem(
    recipe: RecipeUiModel,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(shapeDefault)

    Card(
        onClick = { onClick(recipe.id) },
        modifier = modifier
            .height(recipeItemHeight)
            .fillMaxWidth(),
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = shadow),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            RecipeImage(
                imageUrl = recipe.imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )

            Text(
                text = recipe.title.uppercase(LocalConfiguration.current.locales[0]),
                maxLines = 1,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(padding8)
            )
        }
    }
}

@Preview
@Composable
fun RecipeItemPreview() {
    RecipeComposeAppTheme {
        RecipeItem(RecipeUiModel(1, "test name", "test_url", emptyList(), emptyList()), {})
    }
}