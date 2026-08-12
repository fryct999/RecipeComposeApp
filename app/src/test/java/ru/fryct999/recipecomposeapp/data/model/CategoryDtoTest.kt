package ru.fryct999.recipecomposeapp.data.model

import fixtures.CategoryTestFixtures
import org.junit.Assert.*
import org.junit.Test
import ru.fryct999.recipecomposeapp.ui.Constants

class CategoryDtoTest {
    @Test
    fun `converts DTO to UI model`() {
        val dto = CategoryTestFixtures.createCategoryDto()
        val result = dto.toUiModel()

        assertEquals(1, result.id)
        assertEquals("Завтраки", result.title)
        assertEquals("Утренние блюда", result.description)
        assertEquals(Constants.IMAGES_BASE_URL + dto.imageUrl, result.imageUrl)
    }

    @Test
    fun `mapper maps empty title correctly`() {
        val dto = CategoryTestFixtures.createCategoryDto(title = "")
        val result = dto.toUiModel()

        assertEquals("", result.title)
    }

    @Test
    fun `mapper preserves very long description`() {
        val dto = CategoryTestFixtures.createCategoryDto(description = "a".repeat(10000))
        val result = dto.toUiModel()

        assertEquals("a".repeat(10000), result.description)
    }
}