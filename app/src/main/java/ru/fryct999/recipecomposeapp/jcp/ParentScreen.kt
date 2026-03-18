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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

class ParentScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeComposeAppTheme {
                ParentScreenExample()
            }
        }
    }
}

@Composable
fun ParentScreenExample() {
    var text by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            ChildComponentExample(text, { text = it })

            Button(
                onClick = { text = "" },
            ) {
                Text(text = "Сбросить")
            }
        }

    }
}

@Preview
@Composable
fun ParentScreenPreview() {
    RecipeComposeAppTheme {
        ParentScreenExample()
    }
}