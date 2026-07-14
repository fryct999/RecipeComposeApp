package ru.fryct999.recipecomposeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.fryct999.recipecomposeapp.data.model.CategoryDto
import ru.fryct999.recipecomposeapp.data.model.RecipeDto
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)
    private val okHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.data?.let { _ ->
            deepLinkIntent = intent
        }

        enableEdgeToEdge()
        setContent {
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }

        thread {
            try {
                Log.i("Thread", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

                val request: Request = Request.Builder()
                    .url("https://recipes.androidsprint.ru/api/category")
                    .build()

                okHttpClient.newCall(request).execute().use { response ->
                    val body = response.body?.string()

                    if (body.isNullOrEmpty()) {
                        Log.w("Thread", "Пустой ответ для списка категорий")
                        return@use
                    }

                    val categoryDto = Json.decodeFromString<List<CategoryDto>>(body)
                    Log.i("Thread", "Всего категорий: ${categoryDto.size}")

                    categoryDto.forEach {
                        threadPool.execute {
                            try {
                                Log.i(
                                    "Thread",
                                    "Выполняю запрос на потоке: ${Thread.currentThread().name}"
                                )

                                val request: Request = Request.Builder()
                                    .url("https://recipes.androidsprint.ru/api/category/${it.id}/recipes")
                                    .build()

                                okHttpClient.newCall(request).execute().use { response ->
                                    val body = response.body?.string()

                                    if (body.isNullOrEmpty()) {
                                        Log.w("Thread", "Пустой ответ для категории ${it.title}")
                                        return@use
                                    }

                                    val recipesDto = Json.decodeFromString<List<RecipeDto>>(body)
                                    Log.i("Thread", "Название категории: ${it.title}")
                                    Log.i("Thread", "Количество рецептов: ${recipesDto.size}")
                                }
                            } catch (e: Exception) {
                                Log.e(
                                    "Thread",
                                    "Ошибка при загрузке категории - ${it.title}: ${e.message}"
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("Thread", "Ошибка при загрузке категорий: ${e.message}")
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { _ ->
            deepLinkIntent = intent
        }

        setIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        threadPool.shutdown()
    }
}