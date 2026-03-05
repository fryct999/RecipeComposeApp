package ru.fryct999.recipecomposeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val RecipesComposeAppLightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    error = AccentColor,
    tertiary = AccentBlue,
    tertiaryContainer = SliderTrackColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    surfaceVariant = SurfaceVariantColor,
    outline = DividerColor,
    onPrimary = BackgroundColor,
    onBackground = TextPrimaryColor,
    onSurface = TextPrimaryColor,
    onError = BackgroundColor,
    onTertiary = BackgroundColor,
    onTertiaryContainer = TextPrimaryColor,
    onSurfaceVariant = TextSecondaryColor
)

private val RecipesComposeAppDarkColorScheme = darkColorScheme(
    primary = PrimaryColorDark,
    error = AccentColorDark,
    tertiary = AccentBlueDark,
    tertiaryContainer = SliderTrackColorDark,
    background = BackgroundColorDark,
    surface = SurfaceColorDark,
    surfaceVariant = SurfaceVariantColorDark,
    outline = DividerColorDark,
    onPrimary = BackgroundColorDark,
    onBackground = TextPrimaryColorDark,
    onSurface = TextPrimaryColorDark,
    onError = BackgroundColorDark,
    onTertiary = BackgroundColorDark,
    onTertiaryContainer = TextPrimaryColorDark,
    onSurfaceVariant = TextSecondaryColorDark
)

@Composable
fun RecipeComposeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (darkTheme) RecipesComposeAppDarkColorScheme else RecipesComposeAppLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = recipesAppTypography,
        content = content
    )
}