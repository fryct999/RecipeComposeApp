package ru.fryct999.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.core.ui.ScreenHeader
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepositoryStub.getRecipesByCategoryId
import ru.fryct999.recipecomposeapp.ui.recipes.model.RecipeUiModel
import ru.fryct999.recipecomposeapp.ui.recipes.model.toUiModel
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding16
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding8
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesScreen(
    categoryId: Int?,
    categoryTitle: String?,
    modifier: Modifier = Modifier,
    onRecipeClick: (Int) -> Unit,
) {
    var recipes by remember { mutableStateOf<List<RecipeUiModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(categoryId) {
        isLoading = true

        try {
            categoryId?.let {
                recipes = getRecipesByCategoryId(it).map { dto -> dto.toUiModel() }
            }
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = modifier
    ) {
        ScreenHeader(
            painter = painterResource(id = R.drawable.bcg_recipes_list),
            contentDescription = "Раздел с рецептами $categoryTitle",
            text = categoryTitle ?: "",
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
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
fun RecipesScreenPreview() {
    RecipeComposeAppTheme {
        RecipesScreen(categoryId = 0, categoryTitle = "Бургеры", onRecipeClick = {})
    }
}