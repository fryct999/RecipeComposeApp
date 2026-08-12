package ru.fryct999.recipecomposeapp.features.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fryct999.recipecomposeapp.core.utils.FavoriteDataStoreManager
import ru.fryct999.recipecomposeapp.data.model.toUiModel
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepository
import ru.fryct999.recipecomposeapp.features.details.presentation.model.RecipeDetailsUiState
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import ru.fryct999.recipecomposeapp.navigation.Constants.PARAM_RECIPE_ID
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository,
    private val favoriteManager: FavoriteDataStoreManager,
) : ViewModel() {
    private val recipeId = savedStateHandle.get<Int>(PARAM_RECIPE_ID) ?: -1
    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    init {
        loadRecipeById()

        viewModelScope.launch {
            favoriteManager.getFavoriteIdsFlow()
                .collect { favoriteIds ->
                    val isFavorite = favoriteIds.contains(recipeId.toString())
                    _uiState.update { it.copy(isFavorite = isFavorite) }
                }
        }
    }

    private fun loadRecipeById() {
        setLoading(true)
        viewModelScope.launch {
            try {
                updatePortions(1)

                repository.getRecipe(recipeId).collect { recipeEntity ->
                    val recipe = recipeEntity?.toUiModel() ?: return@collect
                    setRecipe(recipe)
                    setIngredients(recipe.ingredients)
                    setLoading(false)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                setError("Не удалось загрузить рецепт: ${e.message}")
                setLoading(false)
            }
        }
    }

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

    private fun setRecipe(recipe: RecipeUiModel) {
        _uiState.update { currentState ->
            currentState.copy(recipe = recipe)
        }
    }

    private fun setIngredients(ingredients: List<IngredientUiModel>) {
        _uiState.update { currentState ->
            currentState.copy(ingredients = ingredients)
        }
    }

    fun updatePortions(portionsCount: Int) {
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