package ru.fryct999.recipecomposeapp.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.fryct999.recipecomposeapp.core.network.NetworkModule.apiService
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepositoryImpl
import ru.fryct999.recipecomposeapp.features.categories.ui.CategoriesScreen
import ru.fryct999.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import ru.fryct999.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import ru.fryct999.recipecomposeapp.features.favorites.ui.FavoritesScreen
import ru.fryct999.recipecomposeapp.features.recipes.presentation.RecipesViewModel
import ru.fryct999.recipecomposeapp.features.recipes.ui.RecipesScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val repository = remember { RecipesRepositoryImpl(apiService) }

    NavHost(
        navController = navController,
        startDestination = Destination.Categories.route,
    ) {
        composable(route = Destination.Categories.route) {
            CategoriesScreen(
                repository = repository,
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
            FavoritesScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
                },
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
            val viewModel = remember(backStackEntry) {
                RecipesViewModel(
                    savedStateHandle = backStackEntry.savedStateHandle,
                    repository = repository
                )
            }

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
            val context = LocalContext.current

            val viewModel = remember(backStackEntry) {
                RecipeDetailsViewModel(
                    context.applicationContext as Application,
                    backStackEntry.savedStateHandle,
                    repository
                )
            }
            RecipeDetailsScreen(
                viewModel = viewModel,
                modifier = modifier,
            )
        }
    }
}