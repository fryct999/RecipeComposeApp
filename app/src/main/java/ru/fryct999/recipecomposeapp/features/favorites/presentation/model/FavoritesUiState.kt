package ru.fryct999.recipecomposeapp.features.favorites.presentation.model

import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel

data class FavoritesUiState(
    val favoriteRecipes: List<RecipeUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val isEmpty get() = favoriteRecipes.isEmpty()
}