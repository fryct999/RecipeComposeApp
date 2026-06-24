package ru.fryct999.recipecomposeapp.ui.recipes.model

import org.junit.Assert.*
import org.junit.Test
import ru.fryct999.recipecomposeapp.data.model.IngredientDto
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.toUiModel

class IngredientDTOToUiModelTest {
    @Test
    fun checkMapping() {
        val input = IngredientDto(
            quantity = "1.0",
            unitOfMeasure = "шт.",
            description = "Test ingredient",
        )

        val result = IngredientUiModel(
            amount = "1.0",
            unitOfMeasure = "шт.",
            name = "Test ingredient",
        )

        assertEquals(result, input.toUiModel())
    }
}