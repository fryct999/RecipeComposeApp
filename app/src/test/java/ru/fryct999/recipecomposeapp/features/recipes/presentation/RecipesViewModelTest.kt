package ru.fryct999.recipecomposeapp.features.recipes.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import fixtures.RecipeTestFixtures
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepository
import ru.fryct999.recipecomposeapp.navigation.Constants.CATEGORY_ID
import ru.fryct999.recipecomposeapp.navigation.Constants.CATEGORY_IMAGE_URL
import ru.fryct999.recipecomposeapp.navigation.Constants.CATEGORY_TITLE
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class RecipesViewModelTest {
    private val repository = mockk<RecipesRepository>()
    private lateinit var viewModel: RecipesViewModel

    private val recipesCount = 3

    private fun createViewModel(
        categoryId: Int = 1,
        title: String = "title",
        imageUrl: String = "imageUrl",
    ) = RecipesViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    CATEGORY_TITLE to title,
                    CATEGORY_IMAGE_URL to imageUrl,
                    CATEGORY_ID to categoryId
                )
            ),
            repository = repository
        )

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        every { repository.getRecipesByCategory(1) } returns flowOf(
            RecipeTestFixtures.createRecipeDtoList(recipesCount)
        )

        viewModel = createViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `loads recipes for category`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(recipesCount, state.recipes.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state reflects category title from savedState`() = runTest {
        viewModel = createViewModel(title = "Завтраки")

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("Завтраки", state.categoryTitle)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `shows error when repository throws`() = runTest {
        every { repository.getRecipesByCategory(1) } returns flow { throw IOException() }

        viewModel = createViewModel()
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertNotNull(state.error)
            cancelAndIgnoreRemainingEvents()
        }
    }
}