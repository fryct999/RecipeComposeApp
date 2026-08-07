package ru.fryct999.recipecomposeapp.di

import ru.fryct999.recipecomposeapp.data.repository.RecipesRepository
import ru.fryct999.recipecomposeapp.features.categories.presentation.CategoriesViewModel

class CategoriesViewModelFactory(
    private val repository: RecipesRepository,
) : Factory<CategoriesViewModel> {
    override fun create(): CategoriesViewModel {
        return CategoriesViewModel(recipeRepository = repository)
    }
}