package ru.fryct999.recipecomposeapp.features.categories.ui

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import io.github.kakaocup.compose.node.element.KNode
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.fryct999.recipecomposeapp.MainActivity
import ru.fryct999.recipecomposeapp.features.recipes.ui.RecipesComposeScreen


@RunWith(AndroidJUnit4::class)
class CategoriesE2ETest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.withComposeSupport(),
) {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun categoriesScreenLoadsContent() = run {
        step("Открыть приложение и проверить экран категорий") {
            onComposeScreen<CategoriesComposeScreen>(composeTestRule) {
                categoriesGrid { assertIsDisplayed() }
                loadingIndicator { assertIsNotDisplayed() }
            }
        }
    }

    @Test
    fun clickingCategoryOpensRecipesScreen() = run {
        step("Дождаться загрузки категорий") {
            onComposeScreen<CategoriesComposeScreen>(composeTestRule) {
                categoriesGrid { isDisplayed() }
                loadingIndicator { assertIsNotDisplayed() }
            }
        }

        step("Нажать на первую категорию") {
            onComposeScreen<CategoriesComposeScreen>(composeTestRule) {
                categoryItem { performClick() }
            }
        }

        step("Проверить что открылся экран рецептов") {
            onComposeScreen<RecipesComposeScreen>(composeTestRule) {
                assertIsDisplayed()
            }
        }
    }
}

class CategoriesComposeScreen(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<CategoriesComposeScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = { hasTestTag("categories_screen") },
    ) {
    val loadingIndicator: KNode = child { hasTestTag("loading_indicator") }
    val categoriesGrid: KNode = child { hasTestTag("categories_grid") }
    val categoryItem: KNode = child { hasTestTag("category_item") }
}