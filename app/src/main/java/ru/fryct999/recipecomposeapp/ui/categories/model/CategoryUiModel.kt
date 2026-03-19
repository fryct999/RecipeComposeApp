package ru.fryct999.recipecomposeapp.ui.categories.model

import androidx.compose.runtime.Immutable
import ru.fryct999.recipecomposeapp.data.model.CategoryDto
import ru.fryct999.recipecomposeapp.ui.getImagePath

@Immutable
data class CategoryUiModel(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
)

fun CategoryDto.toUiModel() = CategoryUiModel(
    id = id,
    title = title,
    description = description,
    imageUrl = getImagePath(imageUrl),
)