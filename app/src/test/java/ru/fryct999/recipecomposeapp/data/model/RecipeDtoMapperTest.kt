package ru.fryct999.recipecomposeapp.data.model

import fixtures.RecipeTestFixtures
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.fryct999.recipecomposeapp.ui.Constants

class RecipeDtoMapperTest {
    @Test
    fun `converts RecipeDTO to UI model`() {
        val dto = RecipeTestFixtures.createRecipeDto()
        val result = dto.toUiModel()

        assertEquals(1, result.id)
        assertEquals("Pasta Carbonara", result.title)
        assertEquals(dto.ingredients.map { it.toUiModel() }, result.ingredients)
        assertEquals(listOf("Отварить", "Смешать"), result.method)
    }

    @Test
    fun `prepends base url to relative imageUrl`() {
        val dto = RecipeTestFixtures.createRecipeDto()
        val result = dto.toUiModel()
        assertEquals(Constants.IMAGES_BASE_URL + dto.imageUrl, result.imageUrl)
    }

    @Test
    fun `preserves full imageUrl starting with http`() {
        val dto = RecipeTestFixtures.createRecipeDto(imageUrl = "https://aisprints.ru")
        val result = dto.toUiModel()
        assertEquals(dto.imageUrl, result.imageUrl)
    }
}