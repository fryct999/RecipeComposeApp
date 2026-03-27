package ru.fryct999.recipecomposeapp.ui.recipes

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.ui.recipes.model.RecipeUiModel
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding8
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.recipeItemHeight
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.shadow
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.shapeDefault
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import java.util.Locale

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
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recipe.imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.img_placeholder),
                error = painterResource(R.drawable.img_error),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )

            Text(
                text = recipe.title.uppercase(Locale.getDefault()),
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