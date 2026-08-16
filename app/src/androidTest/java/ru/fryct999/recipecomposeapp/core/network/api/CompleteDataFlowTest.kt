package ru.fryct999.recipecomposeapp.core.network.api

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import ru.fryct999.recipecomposeapp.data.database.RecipesDatabase
import ru.fryct999.recipecomposeapp.data.repository.RecipesRepositoryImpl

class CompleteDataFlowTest {
    private lateinit var database: RecipesDatabase
    private lateinit var mockWebServer: MockWebServer
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java).build()
        mockWebServer = MockWebServer().also { it.start() }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        database.close()
        mockWebServer.shutdown()
    }

    @Test
    fun categoriesAreLoadedFromApiAndStoredInCache() = runTest {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """[{"id":1,"title":"Завтраки","description":"Лёгкие блюда","imageUrl":"breakfast.jpg"}]"""
            ).setResponseCode(200)
        )

        val apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(RecipesApiService::class.java)

        val repository = RecipesRepositoryImpl(
            recipesApiService = apiService,
            database = database,
        )

        repository.getCategories().test {
            assertTrue(awaitItem().isEmpty())
            val loaded = awaitItem()
            assertEquals("Завтраки", loaded.first().title)
            cancelAndIgnoreRemainingEvents()
        }

        val cached = database.categoryDao().getAllCategories().first()
        assertEquals(1, cached.size)
        assertEquals("Завтраки", cached.first().name)
    }
}