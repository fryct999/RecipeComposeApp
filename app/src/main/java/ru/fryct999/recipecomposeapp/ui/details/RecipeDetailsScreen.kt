package ru.fryct999.recipecomposeapp.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.core.ui.ScreenHeader
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepositoryStub.getRecipeById
import ru.fryct999.recipecomposeapp.ui.recipes.IngredientItem
import ru.fryct999.recipecomposeapp.ui.recipes.model.IngredientUiModel
import ru.fryct999.recipecomposeapp.ui.recipes.model.RecipeUiModel
import ru.fryct999.recipecomposeapp.ui.recipes.model.toUiModel
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding10
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding16
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding8
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.shapeDefault
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.sliderThumbHeight
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.sliderThumbRadius
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.sliderThumbWidth
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.sliderTrackRadius
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import kotlin.math.roundToInt

@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    categoryId: Int,
    modifier: Modifier = Modifier,
) {
    var recipe by remember { mutableStateOf<RecipeUiModel?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(categoryId, recipeId) {
        isLoading = true

        try {
            recipe = getRecipeById(categoryId, recipeId)?.toUiModel()

        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val recipeUiModel = recipe ?: return
        var currentPortions by remember { mutableIntStateOf(1) }

        Column(
            verticalArrangement = Arrangement.spacedBy(padding10),
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ScreenHeader(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(recipeUiModel.imageUrl)
                        .placeholder(R.drawable.img_placeholder)
                        .error(R.drawable.img_error)
                        .build(),
                ),
                contentDescription = "Описание рецепта ${recipeUiModel.title}",
                text = recipeUiModel.title,
            )

            PortionsSlider(
                currentPortions = currentPortions,
                onPortionsChange = { num -> currentPortions = num },
                modifier = Modifier.padding(horizontal = padding16),
            )

            val scaledIngredients = remember(currentPortions) {
                val multiplier = currentPortions.toDouble()
                recipeUiModel.ingredients.map { ingredient ->
                    ingredient.copy(
                        amount = ingredient.originalAmount?.let {
                            val amount = (it * multiplier)
                            val num = amount.toInt()
                            val fract = amount % 1.0

                            val fractStr = when {
                                fract >= 0.75 -> "3/4"
                                fract >= 0.5 -> "1/2"
                                fract >= 0.25 -> "1/4"
                                else -> ""
                            }

                            "${if (num != 0) num else ""} $fractStr".trim()
                        } ?: ingredient.amount
                    )
                }
            }

            IngredientsList(
                ingredients = scaledIngredients,
                modifier = Modifier.padding(horizontal = padding16)
            )

            InstructionsList(
                instructions = recipeUiModel.method,
                modifier = Modifier.padding(horizontal = padding16),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortionsSlider(
    currentPortions: Int,
    onPortionsChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "ИНГРЕДИЕНТЫ",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = "Порции: $currentPortions",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Slider(
            value = currentPortions.toFloat(),
            onValueChange = { onPortionsChange(it.roundToInt()) },
            valueRange = 1f..12f,
            steps = 10,
            thumb = {
                Box(
                    modifier = Modifier
                        .size(width = sliderThumbWidth, height = sliderThumbHeight)
                        .clip(RoundedCornerShape(sliderThumbRadius))
                        .background(MaterialTheme.colorScheme.tertiary)
                )
            },
            track = { sliderState ->
                SliderDefaults.Track(
                    sliderState = sliderState,
                    thumbTrackGapSize = 0.dp,
                    modifier = Modifier.clip(RoundedCornerShape(sliderTrackRadius)),
                    colors = SliderDefaults.colors(
                        activeTrackColor = MaterialTheme.colorScheme.tertiaryContainer,
                        activeTickColor = MaterialTheme.colorScheme.tertiaryContainer,
                        inactiveTrackColor = MaterialTheme.colorScheme.tertiaryContainer,
                        inactiveTickColor = MaterialTheme.colorScheme.tertiaryContainer,
                        disabledActiveTrackColor = MaterialTheme.colorScheme.tertiary,
                    ),
                )
            },
        )
    }
}

@Composable
fun IngredientsList(
    ingredients: List<IngredientUiModel>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(shapeDefault)
            ),
    ) {
        ingredients.forEachIndexed { index, item ->
            IngredientItem(
                ingredient = item,
                modifier = Modifier
                    .padding(padding10)
            )

            if (index < ingredients.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = padding10)
                )
            }
        }
    }
}

@Composable
fun InstructionsList(
    instructions: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(padding10),
        modifier = modifier,
    ) {
        Text(
            text = "СПОСОБ ПРИГОТОВЛЕНИЯ",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(padding8),
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(shapeDefault)
                )
                .padding(padding10),
        ) {
            instructions.forEachIndexed { index, item ->
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (index < instructions.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview
@Composable
fun RecipeDetailsPreview() {
    RecipeComposeAppTheme {
        RecipeDetailsScreen(
            recipeId = 0,
            categoryId = 0,
            modifier = Modifier,
        )
    }
}