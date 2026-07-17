package ru.fryct999.recipecomposeapp.data.repository

import ru.fryct999.recipecomposeapp.data.model.CategoryDto
import ru.fryct999.recipecomposeapp.data.model.RecipeDto

interface RecipesRepository {
    suspend fun getCategories(): List<CategoryDto>
    suspend fun getRecipe(id: Int): RecipeDto
    suspend fun getRecipesByCategory(id: Int): List<RecipeDto>
}