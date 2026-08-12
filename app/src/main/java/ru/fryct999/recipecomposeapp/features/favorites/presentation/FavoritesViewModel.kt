package ru.fryct999.recipecomposeapp.features.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fryct999.recipecomposeapp.core.utils.FavoriteDataStoreManager
import ru.fryct999.recipecomposeapp.data.model.toUiModel
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepository
import ru.fryct999.recipecomposeapp.features.favorites.presentation.model.FavoritesUiState
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: RecipesRepository,
    private val favoriteManager: FavoriteDataStoreManager,
) : ViewModel() {
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