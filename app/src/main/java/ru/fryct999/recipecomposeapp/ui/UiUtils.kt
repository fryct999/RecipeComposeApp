package ru.fryct999.recipecomposeapp.ui

fun getImagePath(imageUrl: String): String =
    if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl