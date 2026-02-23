package ru.fryct999.recipecomposeapp.jcp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fryct999.recipecomposeapp.ui.theme.RecipeComposeAppTheme

class SimpleListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleListExample()
        }
    }
}



@Composable
fun SimpleListExample() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth().height(120.dp),
            contentPadding = PaddingValues(15.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            userScrollEnabled = true,

        ) {
            items(20) {
                Text(text = "Horizontal list.")
                Button(onClick = {}) { Text(text = "Button") }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentPadding = PaddingValues(15.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            userScrollEnabled = true,
        ) {
            items(20) {
                Text(text = "Vertical list.")
                Button(onClick = {}) { Text(text = "Button") }
            }

        }
    }
}

@Preview
@Composable
fun SimpleListExamplePreview() {
    RecipeComposeAppTheme {
        SimpleListExample()
    }
}