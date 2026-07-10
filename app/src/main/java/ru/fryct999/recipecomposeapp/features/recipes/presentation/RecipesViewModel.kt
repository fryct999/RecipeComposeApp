package ru.fryct999.recipecomposeapp.features.recipes.presentation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepository
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.toUiModel
import ru.fryct999.recipecomposeapp.navigation.Constants.CATEGORY_ID
import ru.fryct999.recipecomposeapp.navigation.Constants.CATEGORY_IMAGE_URL
import ru.fryct999.recipecomposeapp.navigation.Constants.CATEGORY_TITLE

class RecipesViewModel(
    savedStateHandle: SavedStateHandle,
    recipesRepository: RecipesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    init {
        setLoading(true)
        viewModelScope.launch {
            try {
                val categoryTitle = Uri.decode(savedStateHandle.get<String>(CATEGORY_TITLE))
                val categoryImageUrl = Uri.decode(savedStateHandle.get<String>(CATEGORY_IMAGE_URL))
                val categoryId = savedStateHandle.get<Int>(CATEGORY_ID) ?: -1
                val recipes = recipesRepository.getRecipesByCategoryId(categoryId).map { dto ->
                    dto.toUiModel()
                }

                setRecipes(recipes)
                setTitle(categoryTitle)
                setCategoryImageUrl(categoryImageUrl)
                setLoading(false)
            } catch (e: Exception) {
                setError("Ошибка при загрузке списка рецептов.")
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

    private fun setRecipes(recipes: List<RecipeUiModel>) {
        _uiState.update { currentState ->
            currentState.copy(recipes = recipes)
        }
    }

    private fun setTitle(categoryTitle: String) {
        _uiState.update { currentState ->
            currentState.copy(categoryTitle = categoryTitle)
        }
    }

    private fun setCategoryImageUrl(categoryImageUrl: String) {
        _uiState.update { currentState ->
            currentState.copy(categoryImageUrl = categoryImageUrl)
        }
    }
}