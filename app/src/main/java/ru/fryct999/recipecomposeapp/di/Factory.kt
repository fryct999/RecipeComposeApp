package ru.fryct999.recipecomposeapp.di

interface Factory<T> {
    fun create(): T
}