package ru.fryct999.recipecomposeapp.data.repository

import kotlinx.coroutines.flow.Flow
import ru.fryct999.recipecomposeapp.data.model.CategoryDto
import ru.fryct999.recipecomposeapp.data.model.RecipeDto

interface RecipesRepository {
    fun getCategories(): Flow<List<CategoryDto>>
    fun getRecipe(id: Int): Flow<RecipeDto?>
    fun getRecipesByCategory(categoryId: Int): Flow<List<RecipeDto>>

    fun getRecipesByIds(ids: List<Int>): Flow<List<RecipeDto>>
}