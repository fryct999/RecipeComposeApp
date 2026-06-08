package ru.fryct999.recipecomposeapp.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.core.ui.ScreenHeader
import ru.fryct999.recipecomposeapp.core.utils.FavoriteDataStoreManager
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepositoryStub.getRecipeById
import ru.fryct999.recipecomposeapp.ui.recipes.RecipeItem
import ru.fryct999.recipecomposeapp.ui.recipes.model.toUiModel
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding16
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding8
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun FavoritesScreen(
    favoriteDataStoreManager: FavoriteDataStoreManager,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val favoriteIds by favoriteDataStoreManager
        .getFavoriteIdsFlow()
        .collectAsState(initial = emptySet())

    val recipes = favoriteIds.mapNotNull { id ->
        getRecipeById(id.toIntOrNull() ?: return@mapNotNull null)?.toUiModel()
    }

    Column(
        modifier = modifier
    ) {
        ScreenHeader(
            painter = painterResource(id = R.drawable.bcg_favorites),
            contentDescription = "Раздел избранное",
            text = "ИЗБРАННОЕ",
        )

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

@Preview
@Composable
fun PreviewFavoritesScreen() {
    RecipeComposeAppTheme {
        FavoritesScreen(
            favoriteDataStoreManager = FavoriteDataStoreManager(LocalContext.current),
            onRecipeClick = { _ -> },
            modifier = Modifier,
        )
    }
}