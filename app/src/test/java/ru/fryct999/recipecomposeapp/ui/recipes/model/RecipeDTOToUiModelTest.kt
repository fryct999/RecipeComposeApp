package ru.fryct999.recipecomposeapp.ui.recipes.model

import org.junit.Assert.*
import org.junit.Test
import ru.fryct999.recipecomposeapp.data.model.IngredientDto
import ru.fryct999.recipecomposeapp.data.model.RecipeDto
import ru.fryct999.recipecomposeapp.ui.Constants

class RecipeDTOToUiModelTest {
    @Test
    fun checkMapping() {
        val input = RecipeDto(
            id = 1,
            title = "Test recipe",
            ingredients = listOf(
                IngredientDto(
                    quantity = "1.0",
                    unitOfMeasure = "шт",
                    description = "Test ingredient",
                )
            ),
            method = listOf("Step one", "Step two"),
            imageUrl = "test.png"
        )

        val result = RecipeUiModel(
            id = 1,
            title = "Test recipe",
            ingredients = listOf(
                IngredientUiModel(
                    quantity = "1.0",
                    unitOfMeasure = "шт",
                    name = "Test ingredient",
                )
            ),
            method = listOf("Step one", "Step two"),
            imageUrl = Constants.ASSETS_URI_PREFIX + "test.png",
            isFavorite = false
        )

        assertEquals(result, input.toUiModel())
    }

    @Test
    fun checkMappingEmptyIngredients() {
        val input = RecipeDto(
            id = 1,
            title = "Test recipe",
            ingredients = emptyList(),
            method = listOf("Step one", "Step two"),
            imageUrl = "test.png"
        )

        val result = RecipeUiModel(
            id = 1,
            title = "Test recipe",
            ingredients = emptyList(),
            method = listOf("Step one", "Step two"),
            imageUrl = Constants.ASSETS_URI_PREFIX + "test.png",
            isFavorite = false
        )

        assertEquals(result, input.toUiModel())
    }

    @Test
    fun checkMappingIsFavoriteDefault() {
        val input = RecipeDto(
            id = 1,
            title = "Test recipe",
            ingredients = emptyList(),
            method = listOf("Step one", "Step two"),
            imageUrl = "test.png"
        )

        assertEquals(false, input.toUiModel().isFavorite)
    }
}