package ru.fryct999.recipecomposeapp.features.favorites.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fryct999.recipecomposeapp.core.utils.FavoriteDataStoreManager
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepository
import ru.fryct999.recipecomposeapp.features.favorites.presentation.model.FavoritesUiState
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.toUiModel

class FavoritesViewModel(
    application: Application,
    repository: RecipesRepository,
) : AndroidViewModel(application) {
    private val favoriteManager = FavoriteDataStoreManager(application)

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                favoriteManager.getFavoriteIdsFlow()
                    .flatMapLatest { ids ->
                        val intIds = ids.mapNotNull { it.toIntOrNull() }
                        if (intIds.isEmpty()) {
                            flowOf(emptyList())
                        } else {
                            repository.getRecipesByIds(intIds)
                        }
                    }
                    .collect { recipes ->
                        setFavorites(recipes.map { it.toUiModel() })
                    }
            } catch (e: Exception) {
                setError("Ошибка при загрузке избранных рецептов: ${e.message}")
            }
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