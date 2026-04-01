package ru.fryct999.recipecomposeapp.ui.recipes.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import ru.fryct999.recipecomposeapp.data.model.RecipeDto
import ru.fryct999.recipecomposeapp.ui.getImagePath

@Immutable
@Parcelize
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: List<IngredientUiModel>,
    val method: List<String>,
    val isFavorite: Boolean = false,
) : Parcelable

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    imageUrl = getImagePath(imageUrl),
    ingredients = ingredients.map { it.toUiModel() },
    method = method,
)


