package ru.fryct999.recipecomposeapp.jcp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import java.util.Locale

class TimerScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeComposeAppTheme {
                TimerScreenExample()
            }
        }
    }
}

@Composable
fun TimerScreenExample() {
    var time by remember { mutableIntStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(10L)
            time++
        }
    }
    Scaffold { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier,
                text = formatSecToMMSSMS(time)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { isRunning = true },
                ) {
                    Text(text = "Старт")
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { isRunning = false },
                ) {
                    Text(text = "Пауза")
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        isRunning = false
                        time = 0
                    },
                ) {
                    Text(text = "Сброс")
                }
            }
        }

    }
}

fun formatSecToMMSSMS(sec: Int): String {
    val minutes = sec / 6000
    val seconds = (sec / 100) % 60
    val mSeconds = sec % 100

    return String.format(Locale.US, "%02d:%02d.%02d", minutes, seconds, mSeconds)
}

@Preview
@Composable
fun TimerScreenPreview() {
    RecipeComposeAppTheme {
        TimerScreenExample()
    }
}