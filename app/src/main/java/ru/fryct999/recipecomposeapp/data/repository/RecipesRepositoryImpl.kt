package ru.fryct999.recipecomposeapp.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.fryct999.recipecomposeapp.core.network.api.RecipesApiService
import ru.fryct999.recipecomposeapp.data.model.CategoryDto
import ru.fryct999.recipecomposeapp.data.model.RecipeDto
import kotlin.collections.emptyList

class RecipesRepositoryImpl(
    private val recipesApiService: RecipesApiService,
) : RecipesRepository {
    override suspend fun getCategories(): List<CategoryDto> {
        return withContext(Dispatchers.IO) {
            try {
                recipesApiService.getCategories()
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка загрузки категорий: ${e.message}")
                emptyList()
            }
        }
    }

    override suspend fun getRecipe(id: Int): RecipeDto {
        return withContext(Dispatchers.IO) {
            try {
                recipesApiService.getRecipe(id)
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка при загрузке рецепта с id $id. ${e.message}")
                throw IllegalStateException("Нет рецепта с id: $id")
            }
        }
    }

    override suspend fun getRecipesByCategory(id: Int): List<RecipeDto> {
        return withContext(Dispatchers.IO) {
            try {
                recipesApiService.getRecipesByCategory(id)
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка при загрузке рецептов $id категории. ${e.message}")
                emptyList()
            }
        }
    }
}