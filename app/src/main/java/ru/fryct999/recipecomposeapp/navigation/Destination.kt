package ru.fryct999.recipecomposeapp.navigation

import android.net.Uri

const val PARAM_RECIPE_ID = "param_recipeId"
const val DEEP_LINK_SCHEME = "fryctrecipeapp"
private const val DEEP_LINK_BASE_URL = "https://fryctrecipes.sprint.ru"

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorite : Destination("favorite")

    object Recipes : Destination("recipes/{categoryId}/{categoryTitle}/{categoryImgUrl}") {
        fun createRoute(categoryId: Int, categoryTitle: String, categoryImgUrl: String) = "recipes/$categoryId/$categoryTitle/${Uri.encode(categoryImgUrl)}"
    }

    object RecipeDetails : Destination("recipe/{param_recipeId}") {
        fun createRoute(recipeId: Int) = "recipe/$recipeId"
    }
}

fun createRecipeDeepLink(recipeId: Int): String = "$DEEP_LINK_BASE_URL/recipe/$recipeId"