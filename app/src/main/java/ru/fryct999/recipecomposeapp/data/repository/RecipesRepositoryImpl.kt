package ru.fryct999.recipecomposeapp.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.fryct999.recipecomposeapp.core.network.api.RecipesApiService
import ru.fryct999.recipecomposeapp.data.database.RecipesDatabase
import ru.fryct999.recipecomposeapp.data.model.CategoryDto
import ru.fryct999.recipecomposeapp.data.model.RecipeDto
import ru.fryct999.recipecomposeapp.data.model.toDto
import ru.fryct999.recipecomposeapp.data.model.toEntity

private const val TAG = "RecipesRepositoryImpl"

class RecipesRepositoryImpl(
    private val recipesApiService: RecipesApiService,
    private val database: RecipesDatabase,
) : RecipesRepository {
    private val categoryDao = database.categoryDao()
    private val recipeDao = database.recipeDao()

    override fun getCategories(): Flow<List<CategoryDto>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fresh = recipesApiService.getCategories()
                categoryDao.insertCategories(fresh.map { it.toEntity() })
                Log.d(TAG, "Обновлено ${fresh.size} категорий")
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка обновления: ${e.message}", e)
            }
        }

        return categoryDao.getAllCategories().map { categories ->
            categories.map { it.toDto() }
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

    override fun getRecipesByCategory(categoryId: Int): Flow<List<RecipeDto>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fresh = recipesApiService.getRecipesByCategory(categoryId)
                recipeDao.insertRecipes(fresh.map { it.toEntity(categoryId) })
                Log.d(TAG, "Обновлено ${fresh.size} рецептов")
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка обновления рецептов: ${e.message} в категории $categoryId", e)
            }
        }

        return recipeDao.getRecipesByCategoryId(categoryId).map { recipesEntity ->
            recipesEntity.map { it.toDto() }
        }
    }

    override fun getRecipesByIds(ids: List<Int>): Flow<List<RecipeDto>> =
        recipeDao.getRecipesById(ids).map { recipeEntity ->
            recipeEntity.map { it.toDto() }
        }
}
