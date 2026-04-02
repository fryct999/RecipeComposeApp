package ru.fryct999.recipecomposeapp.ui.recipes.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import ru.fryct999.recipecomposeapp.data.model.IngredientDto

@Immutable
@Parcelize
data class IngredientUiModel(
    val name: String,
    val amount: String,
    val originalAmount: Double? = null,
    val unitOfMeasure: String,
) : Parcelable

fun IngredientDto.toUiModel() = IngredientUiModel(
    name = description,
    amount = quantity,
    unitOfMeasure = unitOfMeasure,
    originalAmount = quantity.toDoubleOrNull()
)