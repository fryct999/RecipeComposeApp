package ru.fryct999.recipecomposeapp.core.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.fryct999.recipecomposeapp.data.model.CategoryDto
import ru.fryct999.recipecomposeapp.data.model.RecipeDto

interface RecipesApiService {
    @GET("category")
    suspend fun getCategories(): List<CategoryDto>

    @GET("category/{id}/recipes")
    suspend fun getRecipesByCategory(@Path("id") categoryId: Int): List<RecipeDto>
}