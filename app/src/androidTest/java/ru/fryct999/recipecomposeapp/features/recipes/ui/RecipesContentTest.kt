package ru.fryct999.recipecomposeapp.features.recipes.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.RecipesUiState

@RunWith(AndroidJUnit4::class)
class RecipesContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showsLoadingState() {
        composeTestRule.setContent {
            RecipesContent(
                uiState = RecipesUiState(
                    isLoading = true,
                ),
                onRecipeClick = { _ -> },
            )
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun showsErrorState() {
        val errorText = "Network error"
        composeTestRule.setContent {
            RecipesContent(
                uiState = RecipesUiState(
                    error = errorText,
                ),
                onRecipeClick = { _ -> },
            )
        }

        composeTestRule.onNodeWithTag("error_message").assertTextEquals(errorText)
    }

    @Test
    fun showsEmptyState() {
        composeTestRule.setContent {
            RecipesContent(
                uiState = RecipesUiState(),
                onRecipeClick = { _ -> },
            )
        }

        composeTestRule.onNodeWithTag("empty_state").assertIsDisplayed()
    }

    @Test
    fun displaysRecipeList() {
        val recipeName = "Рецепт №1"
        composeTestRule.setContent {
            RecipesContent(
                uiState = RecipesUiState(
                    recipes = listOf(
                        RecipeUiModel(
                            id = 1,
                            title = recipeName,
                            imageUrl = "",
                            ingredients = emptyList(),
                            method = emptyList(),
                        ),
                    ),
                ),
                onRecipeClick = { _ -> },
            )
        }

        composeTestRule.onNodeWithText(recipeName.uppercase()).assertIsDisplayed()
    }
}