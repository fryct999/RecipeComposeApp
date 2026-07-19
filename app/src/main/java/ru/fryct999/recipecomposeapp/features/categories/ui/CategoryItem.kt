package ru.fryct999.recipecomposeapp.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.core.ui.RecipeImage
import ru.fryct999.recipecomposeapp.ui.Constants.DESCRIPTION_LINE_CATEGORY_ITEM
import ru.fryct999.recipecomposeapp.features.categories.presentation.model.CategoryUiModel
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.padding8
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.shadow
import ru.fryct999.recipecomposeapp.ui.theme.Dimens.shapeDefault

@Composable
fun CategoryItem(
    category: CategoryUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(shapeDefault)

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = shadow),
        shape = shape,

        ) {
        RecipeImage(
            imageUrl = category.imageUrl,
            modifier = Modifier.aspectRatio(1.2f),
        )

        Column(
            modifier = Modifier.padding(padding8),
            verticalArrangement = Arrangement.spacedBy(padding8),
        ) {
            Text(
                text = category.title.uppercase(LocalConfiguration.current.locales[0]),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = category.description,
                minLines = DESCRIPTION_LINE_CATEGORY_ITEM,
                maxLines = DESCRIPTION_LINE_CATEGORY_ITEM,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Preview
@Composable
fun CategoryItemPreview() {
    CategoryItem(CategoryUiModel(1, "Заголовок", "Описание", ""), {})
}