package ru.fryct999.recipecomposeapp.data.repository

import app.cash.turbine.test
import fixtures.RecipeTestFixtures
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.fryct999.recipecomposeapp.core.network.api.RecipesApiService
import ru.fryct999.recipecomposeapp.data.database.RecipesDatabase
import ru.fryct999.recipecomposeapp.data.database.dao.CategoryDao
import ru.fryct999.recipecomposeapp.data.database.dao.RecipeDao
import ru.fryct999.recipecomposeapp.data.database.entity.CategoryEntity
import ru.fryct999.recipecomposeapp.data.model.toEntity
import java.io.IOException

class RecipesRepositoryTest {
    private val apiService = mockk<RecipesApiService>()
    private val database = mockk<RecipesDatabase>(relaxed = true)
    private val categoryDao = mockk<CategoryDao>()
    private val recipeDao = mockk<RecipeDao>()

    private lateinit var repository: RecipesRepositoryImpl

    @Before
    fun setup() {
        every { database.categoryDao() } returns categoryDao
        every { database.recipeDao() } returns recipeDao
        repository = RecipesRepositoryImpl(apiService, database)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getCategories emits categories from database`() = runTest {
        every { categoryDao.getAllCategories() } returns flowOf(
            listOf(
                CategoryEntity(
                    id = 1,
                    name = "Завтраки",
                    description = "Утренние блюда",
                    imageUrl = "breakfast.jpg"
                )
            )
        )

        coEvery { apiService.getCategories() } returns emptyList()
        coEvery { categoryDao.insertCategories(any()) } just Runs

        repository.getCategories().test {
            val categories = awaitItem()
            assertEquals(1, categories.size)
            assertEquals("Завтраки", categories[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCategories still emits data when api throws exception`() = runTest {
        every { categoryDao.getAllCategories() } returns flowOf(
            listOf(
                CategoryEntity(
                    id = 1,
                    name = "Завтраки",
                    description = "Утренние блюда",
                    imageUrl = "breakfast.jpg"
                )
            )
        )

        coEvery { apiService.getCategories() } throws IOException("Network error")

        repository.getCategories().test {
            val categories = awaitItem()
            assertEquals(1, categories.single().id)
            assertEquals("Завтраки", categories.single().title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getRecipesByCategory returns flow filtered by categoryId`() = runTest {
        val recipeDtoFirst = RecipeTestFixtures.createRecipeDto(id = 1, title = "Хлеб")
        val recipeDtoSecond = RecipeTestFixtures.createRecipeDto(id = 2, title = "Сосиска")

        every { recipeDao.getRecipesByCategoryId(categoryId = 1) } returns flowOf(
            listOf(
                recipeDtoFirst.toEntity(categoryId = 1),
                recipeDtoSecond.toEntity(categoryId = 1),
            )
        )

        coEvery { apiService.getRecipesByCategory(1) } returns emptyList()

        repository.getRecipesByCategory(1).test {
            val recipes = awaitItem()
            assertEquals(1, recipes[0].id)
            assertEquals("Хлеб", recipes[0].title)
            assertEquals(2, recipes[1].id)
            assertEquals("Сосиска", recipes[1].title)

            cancelAndIgnoreRemainingEvents()
        }

        verify { recipeDao.getRecipesByCategoryId(1) }
    }
}