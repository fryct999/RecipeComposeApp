package ru.fryct999.recipecomposeapp.core.utils

import ru.fryct999.recipecomposeapp.ui.Constants

fun getImagePath(imageUrl: String): String =
    if (imageUrl.startsWith("http")) imageUrl else Constants.IMAGES_BASE_URL + imageUrl