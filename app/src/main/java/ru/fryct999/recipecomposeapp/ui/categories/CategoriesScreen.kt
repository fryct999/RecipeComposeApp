package ru.fryct999.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.ui.components.ScreenHeader

@Composable
fun CategoriesScreen(
    contentPadding: PaddingValues
) {
    ScreenHeader(
        painter = painterResource(id = R.drawable.img_ervar2),
        contentDescription = "Раздел категории",
        text = "КАТЕГОРИИ",
        modifier = Modifier.padding(top = contentPadding.calculateTopPadding())
    )
}