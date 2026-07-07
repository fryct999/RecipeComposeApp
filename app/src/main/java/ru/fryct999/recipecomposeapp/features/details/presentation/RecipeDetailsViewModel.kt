package ru.fryct999.recipecomposeapp.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fryct999.recipecomposeapp.core.utils.FavoriteDataStoreManager
import ru.fryct999.recipecomposeapp.data.model.RecipeDto
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepository
import ru.fryct999.recipecomposeapp.features.details.presentation.model.RecipeDetailsUiState
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.toUiModel
import ru.fryct999.recipecomposeapp.navigation.Constants.PARAM_RECIPE_ID

class RecipeDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val recipesRepository: RecipesRepository,
) : AndroidViewModel(application) {
    private val favoriteManager = FavoriteDataStoreManager(application)
    private val recipeId = savedStateHandle.get<Int>(PARAM_RECIPE_ID) ?: -1
    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    init {
        setLoading(true)
        viewModelScope.launch {
            try {
                val recipe = loadRecipe(recipeId)
                val ingredientsList = recipe?.ingredients?.map { ingredient ->
                    ingredient.toUiModel()
                } ?: emptyList()

                setRecipe(recipe?.toUiModel())
                setPortionCount(1)
                setIngredients(ingredientsList)
                setLoading(false)
            } catch (e: Exception) {
                setError("Ошибка при загрузке списка ингредиентов.")
                setLoading(false)
            }
        }

        viewModelScope.launch {
            favoriteManager.getFavoriteIdsFlow()
                .collect { favoriteIds ->
                    val isFavorite = favoriteIds.contains(recipeId.toString())
                    _uiState.update { it.copy(isFavorite = isFavorite) }
                }
        }
    }

    private fun loadRecipe(recipeId: Int): RecipeDto? = recipesRepository.getRecipeById(recipeId)

    private fun setLoading(loading: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isLoading = loading)
        }
    }

    private fun setError(error: String) {
        _uiState.update { currentState ->
            currentState.copy(error = error)
        }
    }

    private fun setRecipe(recipe: RecipeUiModel?) {
        _uiState.update { currentState ->
            currentState.copy(recipe = recipe)
        }
    }

    private fun setIngredients(ingredients: List<IngredientUiModel>) {
        _uiState.update { currentState ->
            currentState.copy(ingredients = ingredients)
        }
    }

    fun setPortionCount(portionsCount: Int) {
        _uiState.update { currentState ->
            currentState.copy(portionsCount = portionsCount)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            if (_uiState.value.isFavorite) {
                favoriteManager.removeFavorite(recipeId)
            } else {
                favoriteManager.addFavorite(recipeId)
            }
        }
    }
}