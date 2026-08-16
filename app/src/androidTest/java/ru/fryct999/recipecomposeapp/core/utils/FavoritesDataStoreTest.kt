package ru.fryct999.recipecomposeapp.core.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FavoritesDataStoreTest {
    private lateinit var context: Context
    private lateinit var manager: FavoriteDataStoreManager

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        manager = FavoriteDataStoreManager(context)
    }

    @After
    fun tearDown() {
        runBlocking { context.dataStore.edit { it.clear() } }
    }

    @Test
    fun addFavoriteSavesRecipeId() = runTest {
        val recipeId = 42
        manager.addFavorite(recipeId = recipeId)
        assertTrue(manager.isFavoriteFlow(recipeId).first())
    }

    @Test
    fun removeFromFavoritesDeletesRecipeId() = runTest {
        val recipeId = 42
        manager.addFavorite(recipeId = recipeId)
        manager.removeFavorite(recipeId = recipeId)
        assertFalse(manager.isFavoriteFlow(recipeId).first())
    }

    @Test
    fun favoritesFlowEmitsUpdatesReactively() = runTest {
        val recipeId = 42
        manager.getFavoriteIdsFlow().test {
            assertEquals(emptySet<String>(), awaitItem())
            manager.addFavorite(recipeId)
            assertEquals(setOf("$recipeId"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}