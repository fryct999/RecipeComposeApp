package ru.fryct999.recipecomposeapp.features.details.presentation.model

import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel

data class RecipeDetailsUiState(
    val recipe: RecipeUiModel? = null,
    val ingredients: List<IngredientUiModel> = emptyList(),
    val portionsCount: Int = 1,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val scaledIngredients: List<IngredientUiModel>
        get() = ingredients.map { ingredient ->
            ingredient.copy(
                amount = ingredient.originalAmount?.let {
                    val amount = it * portionsCount
                    val num = amount.toInt()
                    val fract = amount % 1.0

                    val fractStr = when {
                        fract >= 0.75 -> "3/4"
                        fract >= 0.5 -> "1/2"
                        fract >= 0.25 -> "1/4"
                        else -> ""
                    }

                    "${if (num != 0) num else ""} $fractStr".trim()
                } ?: ingredient.amount
            )
        }
}