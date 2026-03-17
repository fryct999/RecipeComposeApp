package ru.fryct999.recipecomposeapp.ui.favorites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.core.ui.ScreenHeader

@Composable
fun FavoritesScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    ScreenHeader(
        painter = painterResource(id = R.drawable.bcg_favorites),
        contentDescription = "Раздел избранное",
        text = "ИЗБРАННОЕ",
        modifier = Modifier
            .padding(top = contentPadding.calculateTopPadding())
            .then(modifier)
    )
}