package ru.fryct999.recipecomposeapp

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.features.categories.ui.CategoriesScreen
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import ru.fryct999.recipecomposeapp.core.utils.FavoriteDataStoreManager
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepositoryStub
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepositoryStub.getRecipeById
import ru.fryct999.recipecomposeapp.navigation.DEEP_LINK_SCHEME
import ru.fryct999.recipecomposeapp.navigation.Destination
import ru.fryct999.recipecomposeapp.navigation.PARAM_RECIPE_ID
import ru.fryct999.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import ru.fryct999.recipecomposeapp.features.favorites.ui.FavoritesScreen
import ru.fryct999.recipecomposeapp.ui.navigation.BottomNavigation
import ru.fryct999.recipecomposeapp.features.recipes.ui.RecipesScreen
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesApp(
    deepLinkIntent: Intent?
) {
    RecipeComposeAppTheme {
        val navController = rememberNavController()
        val context = LocalContext.current
        val favoriteDataStoreManager = remember { FavoriteDataStoreManager(context) }

        LaunchedEffect(deepLinkIntent) {
            deepLinkIntent?.data?.let { uri ->
                val recipeId: Int? = when (uri.scheme) {
                    DEEP_LINK_SCHEME ->
                        if (uri.host == "recipe") uri.pathSegments.getOrNull(0)
                            ?.toIntOrNull() else null

                    "https", "http" ->
                        if (uri.pathSegments.getOrNull(0) == "recipe") uri.pathSegments.getOrNull(1)
                            ?.toIntOrNull() else null

                    else -> null
                }

                if (recipeId != null) {
                    delay(100)
                    navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
                }
            }
        }

        Scaffold(
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = {
                        navController.navigate(Destination.Categories.route)
                    },
                    onFavoriteClick = {
                        navController.navigate(Destination.Favorite.route)
                    },
                    favoriteDataStoreManager = favoriteDataStoreManager,
                )
            },
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Destination.Categories.route,
            ) {
                composable(route = Destination.Favorite.route) {
                    FavoritesScreen(
                        favoriteDataStoreManager = favoriteDataStoreManager,
                        recipesRepository = RecipesRepositoryStub,
                        onRecipeClick = { recipeId ->
                            navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
                        },
                        modifier = Modifier.padding(paddingValues),
                    )
                }

                composable(route = Destination.Categories.route) {
                    CategoriesScreen(
                        onCategoryClick = { categoryId, categoryTitle ->
                            navController.navigate(
                                Destination.Recipes.createRoute(
                                    categoryId,
                                    categoryTitle
                                )
                            )
                        },
                        modifier = Modifier.padding(paddingValues),
                    )
                }

                composable(
                    route = Destination.Recipes.route,
                    arguments = listOf(
                        navArgument("categoryId") { type = NavType.IntType },
                        navArgument("categoryTitle") { type = NavType.StringType },
                    ),
                ) { backStackEntry ->
                    val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
                    val categoryTitle = backStackEntry.arguments?.getString("categoryTitle") ?: ""

                    RecipesScreen(
                        categoryId = categoryId,
                        categoryTitle = categoryTitle,
                        onRecipeClick = { recipeId ->
                            navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
                        },
                        modifier = Modifier.padding(paddingValues),
                    )
                }

                composable(
                    route = Destination.RecipeDetails.route,
                    arguments = listOf(
                        navArgument(PARAM_RECIPE_ID) { type = NavType.IntType },
                    ),
                ) { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getInt(PARAM_RECIPE_ID) ?: 0
                    val recipe = getRecipeById(recipeId)

                    recipe?.let {
                        RecipeDetailsScreen(
                            recipeId = recipeId,
                            favoriteDataStoreManager = favoriteDataStoreManager,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RecipesAppPreview() {
    RecipesApp(null)
}