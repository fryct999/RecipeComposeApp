package ru.fryct999.recipecomposeapp.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.buttonSpacer
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding10
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.shapeDefault
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun BottomNavigation(onCategoriesClick: () -> Unit, onFavoriteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = padding10, bottom = padding10),
        horizontalArrangement = Arrangement.spacedBy(buttonSpacer)
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = onCategoriesClick,
            shape = RoundedCornerShape(shapeDefault),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Text(
                text = "КАТЕГОРИИ",
                style = MaterialTheme.typography.labelLarge,
            )
        }

        Button(
            modifier = Modifier.weight(1f),
            onClick = onFavoriteClick,
            shape = RoundedCornerShape(shapeDefault),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "ИЗБРАННОЕ",
                    style = MaterialTheme.typography.labelLarge,
                )

                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_favorite_small),
                    contentDescription = "favorite",
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomNavigationPreview() {
    RecipeComposeAppTheme {
        BottomNavigation({}, {})
    }
}