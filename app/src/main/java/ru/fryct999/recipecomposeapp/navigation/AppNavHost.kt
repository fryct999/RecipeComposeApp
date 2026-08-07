package ru.fryct999.recipecomposeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.fryct999.recipecomposeapp.features.categories.ui.CategoriesScreen
import ru.fryct999.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import ru.fryct999.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import ru.fryct999.recipecomposeapp.features.favorites.presentation.FavoritesViewModel
import ru.fryct999.recipecomposeapp.features.favorites.ui.FavoritesScreen
import ru.fryct999.recipecomposeapp.features.recipes.presentation.RecipesViewModel
import ru.fryct999.recipecomposeapp.features.recipes.ui.RecipesScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Categories.route,
    ) {
        composable(route = Destination.Categories.route) {
            CategoriesScreen(
                onCategoryClick = { categoryId, categoryTitle, categoryImageUrl ->
                    navController.navigate(
                        Destination.Recipes.createRoute(
                            categoryId,
                            categoryTitle,
                            categoryImageUrl,
                        )
                    )
                },
                modifier = modifier,
            )
        }

        composable(route = Destination.Favorite.route) {
            val viewModel: FavoritesViewModel = hiltViewModel()

            FavoritesScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
                },
                viewModel = viewModel,
                modifier = modifier,
            )
        }

        composable(
            route = Destination.Recipes.route,
            arguments = listOf(
                navArgument(Constants.CATEGORY_ID) { type = NavType.IntType },
                navArgument(Constants.CATEGORY_TITLE) { type = NavType.StringType },
                navArgument(Constants.CATEGORY_IMAGE_URL) { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val viewModel: RecipesViewModel = hiltViewModel()

            RecipesScreen(
                viewModel = viewModel,
                onRecipeClick = { recipeId ->
                    navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
                },
                modifier = modifier,
            )
        }

        composable(
            route = Destination.RecipeDetails.route,
            arguments = listOf(
                navArgument(Constants.PARAM_RECIPE_ID) { type = NavType.IntType },
            ),
        ) { backStackEntry ->
            val viewModel: RecipeDetailsViewModel = hiltViewModel()

            RecipeDetailsScreen(
                viewModel = viewModel,
                modifier = modifier,
            )
        }
    }
}