package ru.fryct999.recipecomposeapp.core.utils

import android.content.Context
import androidx.core.content.edit

class FavoritePrefsManager(
    context: Context,
) {
    private val sharedPreferences =
        context.getSharedPreferences("recipe_app_prefs", Context.MODE_PRIVATE)

    fun isFavorite(recipeId: Int): Boolean {
        val favoriteRecipeIds = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())
        return favoriteRecipeIds?.contains(recipeId.toString()) ?: false
    }

    fun addToFavorites(recipeId: Int) {
        val favoriteRecipeIds = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())
        val updatedFavorites = favoriteRecipeIds?.toMutableSet() ?: mutableSetOf()
        updatedFavorites.add(recipeId.toString())
        sharedPreferences.edit {
            putStringSet("favorite_recipe_ids", updatedFavorites)
        }
    }

    fun removeFromFavorites(recipeId: Int) {
        val favoriteRecipeIds = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())
        val updatedFavorites = favoriteRecipeIds?.toMutableSet() ?: mutableSetOf()
        updatedFavorites.removeIf { it == recipeId.toString() }
        sharedPreferences.edit {
            putStringSet("favorite_recipe_ids", updatedFavorites)
        }
    }

    fun getAllFavorites(): Set<String> {
        val favoriteRecipeIds = sharedPreferences.getStringSet("favorite_recipe_ids", emptySet())
        return favoriteRecipeIds ?: emptySet()
    }
}