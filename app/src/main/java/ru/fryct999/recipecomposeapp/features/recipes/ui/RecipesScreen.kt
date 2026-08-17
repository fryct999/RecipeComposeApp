package ru.fryct999.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import coil3.compose.rememberAsyncImagePainter
import ru.fryct999.recipecomposeapp.core.ui.ScreenHeader
import ru.fryct999.recipecomposeapp.features.recipes.presentation.RecipesViewModel
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding16
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding8

@Composable
fun RecipesScreen(
    viewModel: RecipesViewModel,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    RecipesContent(uiState = uiState, onRecipeClick = onRecipeClick, modifier = modifier)
}

@Composable
fun RecipesContent(
    uiState: RecipesUiState,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .testTag("recipes_screen"),
    ) {
        ScreenHeader(
            painter = rememberAsyncImagePainter(uiState.categoryImageUrl),
            contentDescription = "Раздел с рецептами ${uiState.categoryTitle}",
            text = uiState.categoryTitle,
        )

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.testTag("loading_indicator"))
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.error ?: "Непредвиденная ошибка",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.testTag("error_message"),
                )
            }
        } else if (uiState.isEmpty) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "В этой категории пока нет рецептов",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.testTag("empty_state"),
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(uiState.recipes, key = { it.id }) { recipe ->
                    RecipeItem(
                        recipe = recipe,
                        onClick = { onRecipeClick(recipe.id) },
                        modifier = Modifier.padding(horizontal = padding16, vertical = padding8)
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun RecipesScreenPreview() {
//    RecipeComposeAppTheme {
//        RecipesScreen(
//            backStackEntry = NavBackStackEntry(),
//            onRecipeClick = { _ -> },
//        )
//    }
//}