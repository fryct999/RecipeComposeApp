package ru.fryct999.recipecomposeapp.features.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.fryct999.recipecomposeapp.data.model.toUiModel
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepository
import ru.fryct999.recipecomposeapp.features.categories.presentation.model.CategoriesUiState
import ru.fryct999.recipecomposeapp.features.categories.presentation.model.CategoryUiModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val recipeRepository: RecipesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                recipeRepository.getCategories().collect { categories ->
                    setCategories(categories.map { it.toUiModel() })
                }
            } catch (e: Exception) {
                setError("Ошибка при загрузке списка категорий. ${e.message}")
            }
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