package ru.fryct999.recipecomposeapp.ui.recipes.model

import androidx.compose.runtime.Immutable
import ru.fryct999.recipecomposeapp.data.model.RecipeDto
import ru.fryct999.recipecomposeapp.ui.getImagePath

@Immutable
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: List<IngredientUiModel>,
    val method: List<String>,
    val isFavorite: Boolean = false,
)

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    imageUrl = getImagePath(imageUrl),
    ingredients = ingredients.map { it.toUiModel() },
    method = method,
)


