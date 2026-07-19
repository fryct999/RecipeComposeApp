package ru.fryct999.recipecomposeapp.features.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepository
import ru.fryct999.recipecomposeapp.features.categories.presentation.model.CategoriesUiState
import ru.fryct999.recipecomposeapp.features.categories.presentation.model.CategoryUiModel
import ru.fryct999.recipecomposeapp.features.categories.presentation.model.toUiModel

class CategoriesViewModel(
    private val recipeRepository: RecipesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        setLoading(true)
        viewModelScope.launch {
            try {
                setCategories(recipeRepository.getCategories().map { it.toUiModel() })
            } catch (e: Exception) {
                setError("Ошибка при загрузке списка категорий. ${e.message}")
            } finally {
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

class CategoriesViewModelFactory(
    private val repository: RecipesRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoriesViewModel(repository) as T
    }
}