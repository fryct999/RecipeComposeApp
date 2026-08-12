package ru.fryct999.recipecomposeapp.data.model

import org.junit.Assert.*
import org.junit.Test

class IngredientDtoTest {
    @Test
    fun `converts DTO to UI model`() {
        val dto = IngredientDto(
            quantity = "4",
            unitOfMeasure = "шт",
            description = "лломтика бекона",
        )
        val result = dto.toUiModel()

        assertEquals("лломтика бекона", result.name)
        assertEquals("4", result.amount)
        assertEquals("шт", result.unitOfMeasure)
        assertEquals(4.0, result.originalAmount)
    }
}