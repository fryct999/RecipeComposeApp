package ru.fryct999.recipecomposeapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepository
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRecipesRepository(impl: RecipesRepositoryImpl): RecipesRepository
}