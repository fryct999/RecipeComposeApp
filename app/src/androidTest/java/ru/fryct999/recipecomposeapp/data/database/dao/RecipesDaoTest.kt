package ru.fryct999.recipecomposeapp.data.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.fryct999.recipecomposeapp.data.database.RecipesDatabase
import ru.fryct999.recipecomposeapp.data.database.entity.CategoryEntity
import ru.fryct999.recipecomposeapp.data.database.entity.RecipeEntity

class RecipesDaoTest {
    private lateinit var database: RecipesDatabase
    private lateinit var categoryDao: CategoryDao
    private lateinit var recipeDao: RecipeDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        categoryDao = database.categoryDao()
        recipeDao = database.recipeDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertsAndRetrievesCategories() = runTest {
        val categories = listOf(
            CategoryEntity(id = 1, name = "Завтраки", description = "Лёгкие", imageUrl = ""),
            CategoryEntity(id = 2, name = "Обеды", description = "Основные", imageUrl = ""),
        )

        categoryDao.insertCategories(categories)
        val retrieved = categoryDao.getAllCategories().first()

        assertEquals(2, retrieved.size)
    }

    @Test
    fun insertReplacesDuplicateCategory() = runTest {
        val categories = listOf(
            CategoryEntity(id = 1, name = "Завтраки", description = "Лёгкие", imageUrl = ""),
            CategoryEntity(id = 1, name = "Обеды", description = "Основные", imageUrl = ""),
        )

        categoryDao.insertCategories(categories)
        val categoriesList = categoryDao.getAllCategories().first()
        val updated = categoriesList.first()

        assertEquals(1, categoriesList.size)
        assertEquals("Обеды", updated.name)
        assertEquals("Основные", updated.description)
    }

    @Test
    fun getRecipesByCategoryReturnsCorrectItems() = runTest {
        val categories = listOf(
            CategoryEntity(id = 1, name = "Завтраки", description = "Лёгкие", imageUrl = ""),
            CategoryEntity(id = 2, name = "Пиццы", description = "Основные", imageUrl = ""),
        )

        val recipes = listOf(
            RecipeEntity(
                id = 1,
                title = "Блинчики",
                categoryId = 1,
                imageUrl = "",
                ingredients = "",
                method = "",
            ),
            RecipeEntity(
                id = 2,
                title = "Каша",
                categoryId = 1,
                imageUrl = "",
                ingredients = "",
                method = "",
            ),
            RecipeEntity(
                id = 3,
                title = "Пицца 4 сыра",
                categoryId = 2,
                imageUrl = "",
                ingredients = "",
                method = "",
            ),
        )

        categoryDao.insertCategories(categories)
        recipeDao.insertRecipes(recipes)

        val recipesList = recipeDao.getRecipesByCategoryId(1).first()
        assertEquals(2, recipesList.size)
        assertEquals(1, recipesList[0].categoryId)
        assertEquals(1, recipesList[1].categoryId)
    }

    @Test
    fun emptyDatabaseReturnsEmptyList() = runTest {
        val recipesList = recipeDao.getAllRecipes().first()
        val categoriesList = categoryDao.getAllCategories().first()

        assertTrue(recipesList.isEmpty())
        assertTrue(categoriesList.isEmpty())
    }
}