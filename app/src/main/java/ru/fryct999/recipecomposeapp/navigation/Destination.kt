package ru.fryct999.recipecomposeapp.navigation

import android.net.Uri
import ru.fryct999.recipecomposeapp.navigation.Constants.DEEP_LINK_BASE_URL

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorite : Destination("favorite")

    object Recipes : Destination("recipes/{categoryId}/{categoryTitle}/{categoryImageUrl}") {
        fun createRoute(categoryId: Int, categoryTitle: String, categoryImageUrl: String) = "recipes/$categoryId/${Uri.encode(categoryTitle)}/${Uri.encode(categoryImageUrl)}"
    }

    object RecipeDetails : Destination("recipe/{param_recipeId}") {
        fun createRoute(recipeId: Int) = "recipe/$recipeId"
    }
}

fun createRecipeDeepLink(recipeId: Int): String = "$DEEP_LINK_BASE_URL/recipe/$recipeId"