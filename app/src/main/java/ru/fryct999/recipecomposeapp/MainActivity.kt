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
import ru.fryct999.recipecomposeapp.data.model.CategoryDto
import ru.fryct999.recipecomposeapp.data.model.RecipeDto
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.data?.let { _ ->
            deepLinkIntent = intent
        }

        enableEdgeToEdge()
        setContent {
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }

        Log.i("Pool", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        threadPool.execute {
            try {
                Log.i("Pool", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

                val connection =
                    URL("https://recipes.androidsprint.ru/api/category").openConnection()

                if (connection is HttpURLConnection) {
                    connection.connect()

                    val body = connection.getInputStream().bufferedReader().readText()
                    val categoryDto = Json.decodeFromString<List<CategoryDto>>(body)
                    Log.i("Pool", "Всего категорий: ${categoryDto.size}")

                    categoryDto.forEach {
                        threadPool.execute {
                            try {
                                Log.i(
                                    "Pool",
                                    "Выполняю запрос на потоке: ${Thread.currentThread().name}"
                                )

                                val connection =
                                    URL("https://recipes.androidsprint.ru/api/category/${it.id}/recipes").openConnection()

                                if (connection is HttpURLConnection) {
                                    connection.connect()

                                    val body =
                                        connection.getInputStream().bufferedReader().readText()
                                    val recipesDto = Json.decodeFromString<List<RecipeDto>>(body)
                                    Log.i("Pool", "Название категории: ${it.title}")
                                    Log.i("Pool", "Колличество рецептов: ${recipesDto.size}")
                                } else {
                                    Log.w(
                                        "Pool",
                                        "Неожиданный тип соединения: ${connection::class.simpleName}"
                                    )
                                }
                            } catch (e: Exception) {
                                Log.e(
                                    "Pool",
                                    "Ошибка при закгрузке категории - ${it.title}: ${e.message}"
                                )
                            }
                        }
                    }
                } else {
                    Log.w("Pool", "Неожиданный тип соединения: ${connection::class.simpleName}")
                }
            } catch (e: Exception) {
                Log.e("Pool", "Ошибка при загрузке категорий: ${e.message}")
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