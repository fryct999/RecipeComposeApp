package ru.fryct999.recipecomposeapp.features.details.ui

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import ru.fryct999.recipecomposeapp.R
import ru.fryct999.recipecomposeapp.core.ui.ScreenHeader
import ru.fryct999.recipecomposeapp.core.utils.shareRecipe
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepositoryStub
import ru.fryct999.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import ru.fryct999.recipecomposeapp.features.details.presentation.RecipeDetailsViewModelFactory
import ru.fryct999.recipecomposeapp.features.recipes.ui.IngredientItem
import ru.fryct999.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel
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
    modifier: Modifier = Modifier,
) {
    val viewModel: RecipeDetailsViewModel = viewModel(
        factory = RecipeDetailsViewModelFactory(RecipesRepositoryStub)
    )

    //val viewModel: RecipeDetailsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (uiState.error != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = uiState.error ?: "Непредвиденная ошибка",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    } else {
        val recipeUiModel = uiState.recipe ?: return
        val currentPortions = uiState.portionsCount

        Column(
            verticalArrangement = Arrangement.spacedBy(padding10),
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val context = LocalContext.current

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
                showShareButton = true,
                onShareClick = { shareRecipe(context, recipeUiModel.id, recipeUiModel.title) },
                showFavoriteButton = true,
                isFavorite = uiState.isFavorite,
                onFavoriteToggle = {
                    viewModel.toggleFavorite()
                },
            )

            PortionsSlider(
                currentPortions = currentPortions,
                onPortionsChange = { num -> viewModel.updatePortions(num) },
                modifier = Modifier.padding(horizontal = padding16),
            )

            IngredientsList(
                ingredients = uiState.scaledIngredients,
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
                    text = "${index + 1}. $item",
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
            modifier = Modifier,
        )
    }
}