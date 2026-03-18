package ru.fryct999.recipecomposeapp.jcp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

class SelectableListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeComposeAppTheme {
                SelectableListExample()
            }
        }
    }
}

@Composable
fun SelectableListExample() {
    val items = List(5) { "Position №${it + 1}" }
    val checkState = remember { mutableStateListOf<Boolean>().apply { addAll(List(5) { false }) } }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn {
                items(items.size) { id ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        Checkbox(
                            checked = checkState[id],
                            onCheckedChange = { checkState[id] = it }
                        )

                        Text(text = items[id])
                    }
                }
            }

            Row {
                Button(
                    onClick = {
                        checkState.replaceAll { true }
                    },
                ) {
                    Text(text = "Select all")
                }

                Button(
                    onClick = {
                        checkState.replaceAll { false }
                    },
                ) {
                    Text(text = "Reject all")
                }
            }
        }
    }
}

@Preview
@Composable
fun SelectableListPreview() {
    RecipeComposeAppTheme {
        SelectableListExample()
    }
}