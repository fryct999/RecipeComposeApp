package ru.fryct999.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.core.ui.ScreenHeader
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepositoryStub
import ru.fryct999.recipecomposeapp.ui.categories.model.toUiModel
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding16
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun CategoriesScreen(
    onCategoryClick: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val categories = remember {
        RecipesRepositoryStub.getCategories().map { it.toUiModel() }
    }

    Column(
        modifier = modifier
    ) {
        ScreenHeader(
            painter = painterResource(id = R.drawable.img_ervar2),
            contentDescription = "Раздел категории",
            text = "КАТЕГОРИИ",
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(padding16),
            horizontalArrangement = Arrangement.spacedBy(padding16),
            verticalArrangement = Arrangement.spacedBy(padding16),
        ) {
            items(categories, key = { it.id }) { category ->
                CategoryItem(
                    category = category,
                    onClick = { onCategoryClick(category.id, category.title) },
                )
            }
        }
    }
}

@Preview
@Composable
fun CategoriesScreenPreview() {
    RecipeComposeAppTheme {
        CategoriesScreen(onCategoryClick = { _, _ -> })
    }
}