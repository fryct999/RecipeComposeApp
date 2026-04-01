package ru.fryct999.recipecomposeapp.navigation

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorite : Destination("favorite")
    object Recipes : Destination("recipes/{categoryId}/{categoryTitle}") {
        fun createRoute(categoryId: Int, categoryTitle: String) = "recipes/$categoryId/$categoryTitle"
    }

    object RecipesDetails : Destination("recipe/{recipeId}") {
        const val KEY_RECIPE_OBJECT = "recipe_details"
        fun createRoute(recipeId: Int) = "recipe/$recipeId"
    }
}