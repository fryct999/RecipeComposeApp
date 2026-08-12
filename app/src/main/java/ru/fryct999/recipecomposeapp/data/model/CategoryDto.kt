package ru.fryct999.recipecomposeapp.data.model

import kotlinx.serialization.Serializable
import ru.fryct999.recipecomposeapp.core.utils.getImagePath
import ru.fryct999.recipecomposeapp.data.database.entity.CategoryEntity
import ru.fryct999.recipecomposeapp.features.categories.presentation.model.CategoryUiModel

@Serializable
data class CategoryDto(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
)

fun CategoryDto.toEntity() = CategoryEntity(
    id = id,
    name = title,
    description = description,
    imageUrl = imageUrl,
)

fun CategoryEntity.toDto() = CategoryDto(
    id = id,
    title = name,
    description = description,
    imageUrl = imageUrl,
)

fun CategoryDto.toUiModel() = CategoryUiModel(
    id = id,
    title = title,
    description = description,
    imageUrl = getImagePath(imageUrl),
)