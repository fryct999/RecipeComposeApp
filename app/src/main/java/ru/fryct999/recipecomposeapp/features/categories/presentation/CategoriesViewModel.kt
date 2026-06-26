package ru.fryct999.recipecomposeapp.features.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepositoryStub
import ru.fryct999.recipecomposeapp.features.categories.presentation.model.CategoriesUiState
import ru.fryct999.recipecomposeapp.features.categories.presentation.model.CategoryUiModel
import ru.fryct999.recipecomposeapp.features.categories.presentation.model.toUiModel

class CategoriesViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        setLoading(true)
        viewModelScope.launch {
            try {
                setCategories(RecipesRepositoryStub.getCategories().map { it.toUiModel() })
                setLoading(false)
            } catch (e: Exception) {
                setError("Ошибка при загрузке списка рецептов. $e")
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

    private fun setCategories(categories: List<CategoryUiModel>) {
        _uiState.update { currentState ->
            currentState.copy(categories = categories)
        }
    }
}