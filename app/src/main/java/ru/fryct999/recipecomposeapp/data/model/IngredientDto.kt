package ru.fryct999.recipecomposeapp.data.model

import kotlinx.serialization.Serializable
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel

@Serializable
data class IngredientDto(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String,
)

fun IngredientDto.toUiModel() = IngredientUiModel(
    name = description,
    amount = quantity,
    unitOfMeasure = unitOfMeasure,
    originalAmount = quantity.toDoubleOrNull()
)