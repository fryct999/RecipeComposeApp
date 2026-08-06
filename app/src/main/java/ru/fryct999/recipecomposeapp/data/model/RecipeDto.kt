package ru.fryct999.recipecomposeapp.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import ru.fryct999.recipecomposeapp.data.database.converter.Converters
import ru.fryct999.recipecomposeapp.data.database.entity.RecipeEntity

@Serializable
data class RecipeDto(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: List<String>,
    val imageUrl: String,
)

fun RecipeDto.toEntity(categoryId: Int) = RecipeEntity(
    id = id,
    title = title,
    categoryId = categoryId,
    imageUrl = imageUrl,
    ingredients = Json.encodeToString(ingredients),
    method = Converters.fromList(method),
)

fun RecipeEntity.toDto() = RecipeDto(
    id = id,
    title = title,
    ingredients = Json.decodeFromString<List<IngredientDto>>(ingredients),
    method = Converters.fromString(method),
    imageUrl = imageUrl,
)