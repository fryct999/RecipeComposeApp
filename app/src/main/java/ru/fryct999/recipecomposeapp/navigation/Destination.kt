package ru.fryct999.recipecomposeapp.navigation

const val PARAM_RECIPE_ID = "param_recipeId"
const val DEEP_LINK_SCHEME = "fryctrecipeapp"
private const val DEEP_LINK_BASE_URL = "https://fryctrecipes.sprint.ru"

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorite : Destination("favorite")
    object Recipes : Destination("recipes/{categoryId}/{categoryTitle}") {
        fun createRoute(categoryId: Int, categoryTitle: String) = "recipes/$categoryId/$categoryTitle"
    }

    object RecipeDetails : Destination("recipe/{param_recipeId}") {
        fun createRoute(recipeId: Int) = "recipe/$recipeId"
    }
}

fun createRecipeDeepLink(recipeId: Int): String = "$DEEP_LINK_BASE_URL/recipe/$recipeId"