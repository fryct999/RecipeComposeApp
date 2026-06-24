package ru.fryct999.recipecomposeapp.features.favorites.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.map
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.core.ui.ScreenHeader
import ru.fryct999.recipecomposeapp.core.utils.FavoriteDataStoreManager
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepository
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepositoryStub
import ru.fryct999.recipecomposeapp.features.recipes.ui.RecipeItem
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.toUiModel
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding16
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding8
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun FavoritesScreen(
    favoriteDataStoreManager: FavoriteDataStoreManager,
    recipesRepository: RecipesRepository,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val recipes by remember {
        favoriteDataStoreManager.getFavoriteIdsFlow().map { ids ->
            ids.mapNotNull { id ->
                recipesRepository.getRecipeById(id.toIntOrNull() ?: return@mapNotNull null)
                    ?.toUiModel()
            }
        }
    }.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
    ) {
        ScreenHeader(
            painter = painterResource(id = R.drawable.bcg_favorites),
            contentDescription = "Раздел избранное",
            text = "ИЗБРАННОЕ",
        )

        if (recipes.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Список избранного сейчас пуст...",
                    style = MaterialTheme.typography.displayLarge,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(recipes, key = { it.id }) { recipe ->
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

@Preview
@Composable
fun PreviewFavoritesScreen() {
    RecipeComposeAppTheme {
        FavoritesScreen(
            favoriteDataStoreManager = FavoriteDataStoreManager(LocalContext.current),
            recipesRepository = RecipesRepositoryStub,
            onRecipeClick = { _ -> },
            modifier = Modifier,
        )
    }
}