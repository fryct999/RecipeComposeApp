package ru.fryct999.recipecomposeapp.features.categories.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.fryct999.recipecomposeapp.features.categories.presentation.model.CategoriesUiState
import ru.fryct999.recipecomposeapp.features.categories.presentation.model.CategoryUiModel

@RunWith(AndroidJUnit4::class)
class CategoriesContentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysCategories() {
        val categoryName = "Завтраки"
        composeTestRule.setContent {
            CategoriesContent(
                uiState = CategoriesUiState(
                    categories = listOf(
                        CategoryUiModel(
                            id = 1,
                            title = categoryName,
                            description = "",
                            imageUrl = ""
                        )
                    )
                ),
                onCategoryClick = { _, _, _ -> }
            )
        }

        composeTestRule.onNodeWithText(categoryName.uppercase()).assertIsDisplayed()
    }

    @Test
    fun clickingCategoryNavigatesToRecipes() {
        var clickedId: Int? = null
        composeTestRule.setContent {
            CategoriesContent(
                uiState = CategoriesUiState(
                    categories = listOf(
                        CategoryUiModel(
                            id = 1,
                            title = "Завтраки",
                            description = "",
                            imageUrl = ""
                        )
                    )
                ),
                onCategoryClick = { id, _, _ -> clickedId = id },
            )
        }

        composeTestRule.onNodeWithText("ЗАВТРАКИ").performClick()
        assertEquals(1, clickedId)
    }

    @Test
    fun showsLoadingState() {
        composeTestRule.setContent {
            CategoriesContent(
                uiState = CategoriesUiState(
                    isLoading = true,
                ),
                onCategoryClick = { _, _, _ -> },
            )
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }
}