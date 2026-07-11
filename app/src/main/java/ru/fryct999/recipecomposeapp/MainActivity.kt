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
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.data?.let { _ ->
            deepLinkIntent = intent
        }

        enableEdgeToEdge()
        setContent {
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }

        Log.i("!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        val thread = Thread {
            Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            var connection: HttpURLConnection? = null

            try {
                connection =
                    URL("https://recipes.androidsprint.ru/api/category").openConnection() as HttpURLConnection
                connection.connect()

                val body = connection.getInputStream().bufferedReader().use() { it.readText() }
                Log.i("!!!", "responseCode: ${connection.responseCode}")
                Log.i("!!!", "responseMessage: ${connection.responseMessage}")
                Log.i("!!!", "Body: $body")

                val categoryDto = Json.decodeFromString<List<CategoryDto>>(body)

                Log.i("!!!", "Всего категорий: ${categoryDto.size}")
                categoryDto.forEach {
                    Log.i("!!!", "Имя категории: ${it.title}")
                }
            } catch (e: Exception) {
                Log.i("!!!", "Ошибка запроса: ${e.message}")
            } finally {
                connection?.disconnect()
            }
        }
        thread.start()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { _ ->
            deepLinkIntent = intent
        }

        setIntent(intent)
    }
}