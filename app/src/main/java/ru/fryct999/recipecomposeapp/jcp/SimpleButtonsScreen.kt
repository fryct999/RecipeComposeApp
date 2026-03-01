package ru.fryct999.recipecomposeapp.jcp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

class SimpleButtonsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeComposeAppTheme {
                SimpleButtonsExample()
            }
        }
    }
}

@Composable
fun SimpleButtonsExample() {
    var clickCount by remember { mutableIntStateOf(0) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            Text(text = "Click count: $clickCount")

            Button(
                onClick = { clickCount += 1 },
            ) {
                Text(text = "Increase counter")
            }
            Button(
                onClick = {
                    clickCount = if (clickCount - 1 >= 0) clickCount - 1 else 0
                },
            ) {
                Text(text = "Reduce counter")
            }
        }
    }
}

@Preview
@Composable
fun SimpleButtonsPreview() {
    RecipeComposeAppTheme {
        SimpleButtonsExample()
    }
}