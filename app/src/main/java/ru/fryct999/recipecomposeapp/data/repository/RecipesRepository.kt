package ru.fryct999.recipecomposeapp.data.repository

import ru.fryct999.recipecomposeapp.data.model.RecipeDto

interface RecipesRepository {
    fun getRecipeById(id: Int): RecipeDto?
    fun getRecipesByCategoryId(id: Int): List<RecipeDto>
}