package ru.fryct999.recipecomposeapp.navigation

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorite : Destination("favorite")
    object Recipes : Destination("recipes/{categoryId}/{categoryTitle}") {
        fun createRoute(categoryId: Int, categoryTitle: String) = "recipes/$categoryId/$categoryTitle"
    }

    object RecipesDetails : Destination("recipe/{categoryId}/{recipeId}") {
        fun createRoute(categoryId: Int, recipeId: Int) = "recipe/$categoryId/$recipeId"
    }
}