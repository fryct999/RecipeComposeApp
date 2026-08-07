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
import ru.fryct999.recipecomposeapp.di.CategoriesViewModelFactory
import ru.fryct999.recipecomposeapp.di.FavoritesViewModelFactory
import ru.fryct999.recipecomposeapp.di.RecipeApplication
import ru.fryct999.recipecomposeapp.di.RecipeDetailsViewModelFactory
import ru.fryct999.recipecomposeapp.di.RecipesViewModelFactory
import ru.fryct999.recipecomposeapp.features.categories.presentation.CategoriesViewModel
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
    val appContainer = (LocalContext.current.applicationContext as RecipeApplication).appContainer
    val application = LocalContext.current.applicationContext as Application

    NavHost(
        navController = navController,
        startDestination = Destination.Categories.route,
    ) {
        composable(route = Destination.Categories.route) {
            val viewModel: CategoriesViewModel = remember {
                CategoriesViewModelFactory(
                    repository = appContainer.recipesRepository,
                ).create()
            }

            CategoriesScreen(
                viewModel = viewModel,
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
            val viewModel: FavoritesViewModel = remember {
                FavoritesViewModelFactory(
                    application = application,
                    repository = appContainer.recipesRepository,
                ).create()
            }

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
            val viewModel: RecipesViewModel = remember {
                RecipesViewModelFactory(
                    savedStateHandle = backStackEntry.savedStateHandle,
                    repository = appContainer.recipesRepository,
                ).create()
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
            val viewModel: RecipeDetailsViewModel = remember {
                RecipeDetailsViewModelFactory(
                    application = application,
                    savedStateHandle = backStackEntry.savedStateHandle,
                    repository = appContainer.recipesRepository,
                ).create()
            }

            RecipeDetailsScreen(
                viewModel = viewModel,
                modifier = modifier,
            )
        }
    }
}