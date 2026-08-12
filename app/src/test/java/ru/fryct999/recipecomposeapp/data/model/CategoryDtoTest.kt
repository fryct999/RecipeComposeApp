package ru.fryct999.recipecomposeapp.data.model

import org.junit.Assert.*
import org.junit.Test
import ru.fryct999.recipecomposeapp.ui.Constants

class CategoryDtoTest {
    @Test
    fun `converts DTO to UI model`() {
        val dto = CategoryDto(
            id = 1,
            title = "Завтраки",
            description = "Утренние блюда",
            imageUrl = "breakfast.jpg"
        )
        val result = dto.toUiModel()

        assertEquals(1, result.id)
        assertEquals("Завтраки", result.title)
        assertEquals("Утренние блюда", result.description)
        assertEquals(Constants.IMAGES_BASE_URL + dto.imageUrl, result.imageUrl)
    }
}