package ru.fryct999.recipecomposeapp.core.network.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class RecipesApiServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: RecipesApiService

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RecipesApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `categories JSON is deserialized into CategoryDto`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setBody("""[{"id":1,"title":"Завтраки","description":"Лёгкие","imageUrl":"breakfast.jpg"}]""")
                .setResponseCode(200)
        )

        val categories = apiService.getCategories()

        assertEquals(1, categories.size)
        assertEquals("Завтраки", categories.first().title)
        assertEquals("breakfast.jpg", categories.first().imageUrl)
    }
}