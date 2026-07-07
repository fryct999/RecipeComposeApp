package ru.fryct999.recipecomposeapp.features.favorites.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fryct999.recipecomposeapp.core.utils.FavoriteDataStoreManager
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepository
import ru.fryct999.recipecomposeapp.features.favorites.presentation.model.FavoritesUiState
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.toUiModel

class FavoritesViewModel(
    application: Application,
    recipesRepository: RecipesRepository,
) : AndroidViewModel(application) {
    private val favoriteManager = FavoriteDataStoreManager(application)

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            setLoading(true)
            try {
                favoriteManager.getFavoriteIdsFlow()
                    .map { ids ->
                        ids.mapNotNull { id ->
                            recipesRepository.getRecipeById(
                                id.toIntOrNull() ?: return@mapNotNull null
                            )?.toUiModel()
                        }
                    }
                    .collect { recipes ->
                        setFavorites(recipes)
                        setLoading(false)
                    }
            } catch (e: Exception) {
                setError("Ошибка загрузки избранного.")
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

    private fun setFavorites(favorites: List<RecipeUiModel>) {
        _uiState.update { currentState ->
            currentState.copy(favoriteRecipes = favorites)
        }
    }
}