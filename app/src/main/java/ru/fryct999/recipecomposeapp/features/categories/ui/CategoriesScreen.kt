package ru.fryct999.recipecomposeapp.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.core.ui.ScreenHeader
import ru.fryct999.recipecomposeapp.features.categories.presentation.CategoriesViewModel
import ru.fryct999.recipecomposeapp.features.categories.presentation.model.CategoriesUiState
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding16

@Composable
fun CategoriesScreen(
    onCategoryClick: (Int, String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: CategoriesViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    CategoriesContent(uiState = uiState, onCategoryClick = onCategoryClick, modifier = modifier)
}

@Composable
fun CategoriesContent(
    uiState: CategoriesUiState,
    onCategoryClick: (Int, String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .testTag("categories_screen"),
    ) {
        ScreenHeader(
            painter = painterResource(id = R.drawable.img_ervar2),
            contentDescription = "Раздел категории",
            text = "КАТЕГОРИИ",
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
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(padding16),
                horizontalArrangement = Arrangement.spacedBy(padding16),
                verticalArrangement = Arrangement.spacedBy(padding16),
                modifier = Modifier.testTag("categories_grid")
            ) {
                items(uiState.categories, key = { it.id }) { category ->
                    CategoryItem(
                        category = category,
                        onClick = {
                            onCategoryClick(
                                category.id,
                                category.title,
                                category.imageUrl
                            )
                        },
                        modifier = Modifier.testTag("category_item")
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun CategoriesScreenPreview() {
//    RecipeComposeAppTheme {
//        CategoriesScreen(
//            repository = RecipesRepositoryStub,
//            onCategoryClick = { _, _, _ -> }
//        )
//    }
//}