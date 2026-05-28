package ru.fryct999.recipecomposeapp.core.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.headerHeight
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding10
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding16
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.shapeDefault
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun ScreenHeader(
    painter: Painter,
    contentDescription: String,
    text: String,
    modifier: Modifier = Modifier,
    showShareButton: Boolean = false,
    onShareClick: () -> Unit = {},
    showFavoriteButton: Boolean = false,
    isFavorite: Boolean = false,
    onFavoriteToggle: (Boolean) -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(headerHeight)
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Surface(
            shape = RoundedCornerShape(shapeDefault),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(padding16),
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(padding10)
            )
        }

        Column(
            modifier = Modifier
                .padding(top = padding10, end = padding10)
                .align(Alignment.TopEnd)
        ) {
            if (showFavoriteButton) {
                IconButton(
                    onClick = {
                        onFavoriteToggle(!isFavorite)
                    },
                ) {
                    Crossfade(
                        targetState = isFavorite,
                        animationSpec = tween(durationMillis = 300),
                        label = "favorite_animation"
                    ) { isCurrentlyFavorite ->
                        val favoriteIcon = rememberVectorPainter(
                            image = ImageVector.vectorResource(
                                id = if (isCurrentlyFavorite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite_empty
                            )
                        )

                        Icon(
                            painter = favoriteIcon,
                            contentDescription = "Favorite",
                            tint = Color.Unspecified
                        )
                    }
                }
            }

            if (showShareButton) {
                IconButton(
                    onClick = onShareClick,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share_arrows),
                        contentDescription = "Поделиться рецептом",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ScreenHeaderPreview() {
    RecipeComposeAppTheme {
        ScreenHeader(
            painter = painterResource(id = R.drawable.img_ervar2),
            contentDescription = "",
            text = "123",
            showShareButton = true,
            onShareClick = {},
            showFavoriteButton = true,
        )
    }
}