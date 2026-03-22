package ru.fryct999.recipecomposeapp.ui.categories.model

import org.junit.Assert.*
import org.junit.Test
import ru.fryct999.recipecomposeapp.data.model.CategoryDto
import ru.fryct999.recipecomposeapp.ui.Constants

class ToUiModelTest {
    @Test
    fun checkMapping() {
        val input = CategoryDto(
            id = 1,
            title = "Test category",
            description = "Test category description",
            imageUrl = "burgerCategory.png"
        )

        val result = CategoryUiModel(
            id = 1,
            title = "Test category",
            description = "Test category description",
            imageUrl = Constants.ASSETS_URI_PREFIX + "burgerCategory.png"
        )

        assertEquals(result, input.toUiModel())
    }
}